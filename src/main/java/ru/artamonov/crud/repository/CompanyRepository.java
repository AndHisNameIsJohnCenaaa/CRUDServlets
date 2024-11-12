package ru.artamonov.crud.repository;

import ru.artamonov.crud.model.Company;

import java.sql.SQLException;
import java.util.List;

public interface CompanyRepository {
	boolean save(Company company);

	boolean update(Company company);

	// false if not found
	boolean delete(int id);

	// null if not found
	Company get(int id);

	List<Company> getAll();
}
