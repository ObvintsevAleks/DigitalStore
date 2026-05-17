package com.personal.DigitalStore.services;

import com.personal.DigitalStore.dto.CustomerDTO;
import com.personal.DigitalStore.dto.CustomerSaveDTO;
import com.personal.DigitalStore.exceptions.custom.NotFoundInDBException;
import com.personal.DigitalStore.mappers.CustomerMapper;
import com.personal.DigitalStore.models.Customer;
import com.personal.DigitalStore.repositories.CustomerRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private CustomerMapper customerMapper;

    @InjectMocks
    private CustomerService customerService;

    private static final UUID ID = UUID.fromString("8e262c04-a090-11e8-98d0-529269fb1459");
    private static final LocalDate BIRTH_DATE = LocalDate.of(1990, 5, 15);

    private Customer customer() {
        return new Customer(ID, "Ivan", "Petrov", BIRTH_DATE,
                "Lenin St. 1", "Moscow", "Moscow", "Russia",
                "101000", "+79001234567", null, "ivan@mail.com");
    }

    private CustomerDTO customerDTO() {
        return new CustomerDTO(ID, "Ivan", "Petrov", BIRTH_DATE,
                "Lenin St. 1", "Moscow", "Moscow", "Russia",
                "101000", "+79001234567", null, "ivan@mail.com");
    }

    private CustomerSaveDTO customerSaveDTO() {
        return new CustomerSaveDTO("Ivan", "Petrov", BIRTH_DATE,
                "Lenin St. 1", "Moscow", "Moscow", "Russia",
                "101000", "+79001234567", null, "ivan@mail.com");
    }

    @Nested
    @DisplayName("createCustomer")
    class CreateCustomer {

        @Test
        @DisplayName("успешно создаёт и возвращает DTO клиента")
        void success() {
            CustomerSaveDTO saveDTO = customerSaveDTO();
            Customer customer = customer();
            CustomerDTO expected = customerDTO();

            when(customerMapper.toCustomer(saveDTO)).thenReturn(customer);
            when(customerMapper.toCustomerDTO(customer)).thenReturn(expected);

            CustomerDTO result = customerService.createCustomer(saveDTO);

            assertThat(result).isEqualTo(expected);
            verify(customerRepository).save(customer);
        }
    }

    @Nested
    @DisplayName("getCustomerById")
    class GetCustomerById {

        @Test
        @DisplayName("возвращает DTO когда клиент найден")
        void found() {
            Customer customer = customer();
            CustomerDTO expected = customerDTO();

            when(customerRepository.findById(ID)).thenReturn(Optional.of(customer));
            when(customerMapper.toCustomerDTO(customer)).thenReturn(expected);

            CustomerDTO result = customerService.getCustomerById(ID);

            assertThat(result).isEqualTo(expected);
        }

        @Test
        @DisplayName("бросает NotFoundInDBException когда клиент не найден")
        void notFound() {
            when(customerRepository.findById(ID)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> customerService.getCustomerById(ID))
                    .isInstanceOf(NotFoundInDBException.class)
                    .hasMessageContaining(ID.toString());
        }
    }

    @Nested
    @DisplayName("updateCustomer")
    class UpdateCustomer {

        @Test
        @DisplayName("обновляет клиента и сохраняет изменения")
        void updatesWhenDataChanged() {
            Customer customer = customer();
            CustomerDTO originalDTO = customerDTO();
            CustomerDTO updatedDTO = new CustomerDTO(ID, "Petr", "Ivanov", BIRTH_DATE,
                    "Lenin St. 1", "Moscow", "Moscow", "Russia",
                    "101000", "+79001234567", null, "petr@mail.com");

            when(customerRepository.findById(ID)).thenReturn(Optional.of(customer));
            // первый вызов — сравнение, второй — после обновления
            when(customerMapper.toCustomerDTO(customer)).thenReturn(originalDTO, updatedDTO);

            CustomerDTO result = customerService.updateCustomer(updatedDTO);

            verify(customerMapper).updateCustomer(customer, updatedDTO);
            verify(customerRepository).save(customer);
            assertThat(result).isEqualTo(updatedDTO);
        }

        @Test
        @DisplayName("не сохраняет если данные не изменились")
        void noSaveWhenDataUnchanged() {
            Customer customer = customer();
            CustomerDTO dto = customerDTO();

            when(customerRepository.findById(ID)).thenReturn(Optional.of(customer));
            when(customerMapper.toCustomerDTO(customer)).thenReturn(dto);

            CustomerDTO result = customerService.updateCustomer(dto);

            verify(customerRepository, never()).save(any());
            assertThat(result).isEqualTo(dto);
        }

        @Test
        @DisplayName("бросает NotFoundInDBException если клиент не найден")
        void notFound() {
            when(customerRepository.findById(ID)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> customerService.updateCustomer(customerDTO()))
                    .isInstanceOf(NotFoundInDBException.class);
        }
    }

    @Nested
    @DisplayName("deleteCustomerById")
    class DeleteCustomerById {

        @Test
        @DisplayName("успешно удаляет клиента")
        void success() {
            Customer customer = customer();
            CustomerDTO expected = customerDTO();

            when(customerRepository.findById(ID)).thenReturn(Optional.of(customer));
            when(customerMapper.toCustomerDTO(customer)).thenReturn(expected);

            CustomerDTO result = customerService.deleteCustomerById(ID);

            verify(customerRepository).deleteById(ID);
            assertThat(result).isEqualTo(expected);
        }

        @Test
        @DisplayName("бросает NotFoundInDBException если клиент не найден")
        void notFound() {
            when(customerRepository.findById(ID)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> customerService.deleteCustomerById(ID))
                    .isInstanceOf(NotFoundInDBException.class);
        }
    }

    @Nested
    @DisplayName("getCustomerListByFirstname")
    class GetCustomerListByFirstname {

        @Test
        @DisplayName("возвращает список клиентов по имени")
        void found() {
            List<Customer> customers = List.of(customer());
            CustomerDTO dto = customerDTO();

            when(customerRepository.searchByFirstname("Ivan")).thenReturn(Optional.of(customers));
            when(customerMapper.toCustomerDTOs(customers)).thenReturn(List.of(dto));

            List<CustomerDTO> result = customerService.getCustomerListByFirstname("Ivan");

            assertThat(result).containsExactly(dto);
        }

        @Test
        @DisplayName("бросает NotFoundInDBException если ничего не найдено")
        void notFound() {
            when(customerRepository.searchByFirstname("Unknown")).thenReturn(Optional.empty());

            assertThatThrownBy(() -> customerService.getCustomerListByFirstname("Unknown"))
                    .isInstanceOf(NotFoundInDBException.class)
                    .hasMessageContaining("Unknown");
        }
    }

    @Nested
    @DisplayName("getCustomerListByLastname")
    class GetCustomerListByLastname {

        @Test
        @DisplayName("возвращает список клиентов по фамилии")
        void found() {
            List<Customer> customers = List.of(customer());
            CustomerDTO dto = customerDTO();

            when(customerRepository.searchByLastname("Petrov")).thenReturn(Optional.of(customers));
            when(customerMapper.toCustomerDTOs(customers)).thenReturn(List.of(dto));

            List<CustomerDTO> result = customerService.getCustomerListByLastname("Petrov");

            assertThat(result).containsExactly(dto);
        }

        @Test
        @DisplayName("бросает NotFoundInDBException если ничего не найдено")
        void notFound() {
            when(customerRepository.searchByLastname("Unknown")).thenReturn(Optional.empty());

            assertThatThrownBy(() -> customerService.getCustomerListByLastname("Unknown"))
                    .isInstanceOf(NotFoundInDBException.class)
                    .hasMessageContaining("Unknown");
        }
    }
}
