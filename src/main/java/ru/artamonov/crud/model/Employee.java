package ru.artamonov.crud.model;

import lombok.*;
import ru.artamonov.crud.annotation.Required;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Employee extends AbstractNamedEntity {
	@Required
	private String telegramId;

	public Employee(int id, String name, String telegramId) {
		super(id, name);
		this.telegramId = telegramId;
	}

	public Employee(String name, String telegramId) {
		super(name);
		this.telegramId = telegramId;
	}

	public Employee(Employee newEmployee) {
		super(newEmployee.name);
		this.telegramId = newEmployee.telegramId;
	}
}
