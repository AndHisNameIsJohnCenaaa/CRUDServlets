package ru.artamonov.crud.repository.jdbc;

import org.junit.jupiter.api.Test;
import ru.artamonov.crud.model.Company;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.artamonov.crud.CompanyTestData.*;

public class JdbcCompanyRepositoryTest extends AbstractRepositoryTest {

	private final JdbcCompanyRepository repository = JdbcCompanyRepository.getInstance();

	@Test
	void save() {
		repository.save(newCompany);
		Company actual = repository.get(NEW_COMPANY_ID);
		Company expected = new Company(newCompany);
		expected.setId(NEW_COMPANY_ID);
		assertThat(actual).isEqualTo(expected);

	}

	@Test
	void update() {
		repository.update(updatedCompany);
		Company actual = repository.get(updatedCompany.getId());
		assertThat(actual).isEqualTo(updatedCompany);
	}

	@Test
	void delete() {
		repository.delete(COMPANY1_ID);
		List<Company> actual = repository.getAll();
		List<Company> expected = new ArrayList<>(companies);
		expected.remove(company1);
		assertThat(actual).hasSize(1).isEqualTo(expected);
	}

	@Test
	void get() {
		Company actual = repository.get(COMPANY1_ID);
		assertThat(actual).isEqualTo(company1);
	}

	@Test
	void getAll() {
		List<Company> actual = repository.getAll();
		assertThat(actual).hasSize(2).isEqualTo(companies);
	}
}