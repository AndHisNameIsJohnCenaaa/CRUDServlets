package ru.artamonov.crud.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.artamonov.crud.annotation.Required;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Task extends AbstractBaseEntity {
	@Required
	private LocalDate deadline;

	@Required
	private String description;

	public Task(int id, LocalDate deadline, String description) {
		super(id);
		this.deadline = deadline;
		this.description = description;
	}

	public Task(Task task) {
		super(task.getId());
		this.deadline = task.getDeadline();
		this.description = task.getDescription();
	}
}
