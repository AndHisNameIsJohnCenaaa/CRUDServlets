package ru.artamonov.crud;

import ru.artamonov.crud.model.Company;
import java.util.List;

public class CompanyTestData {
	public static final int COMPANY1_ID = 100000;
	public static final int COMPANY2_ID = 100001;
	public static final int NEW_COMPANY_ID = 100011;

	public static final Company company1 = new Company(COMPANY1_ID, "Aston", "aston@mail.ru");
	public static final Company company2 = new Company(COMPANY2_ID, "DEL", "digLeague@mail.ru");
	public static final Company newCompany = new Company("New Company", "new@mail.ru");
	public static final Company updatedCompany = new Company(COMPANY1_ID, "Updated Company", "updated@mail.ru");

	public static final List<Company> companies = List.of(company1, company2);
}