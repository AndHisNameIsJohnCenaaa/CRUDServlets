package ru.artamonov.crud.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.artamonov.crud.annotation.Required;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Company extends AbstractNamedEntity {
	@Required
	private String email;

	public Company(int id, String name, String email) {
		super(id, name);
		this.email = email;
	}

	public Company(String name, String email) {
		super(name);
		this.email = email;
	}

	public Company(Company company) {
		super(company.getName());
		this.email = company.getEmail();
	}
}
