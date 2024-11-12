package ru.artamonov.crud.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.artamonov.crud.annotation.Required;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public abstract class AbstractNamedEntity extends AbstractBaseEntity {

	@Required
    protected String name;
	public AbstractNamedEntity(int id, String name) {
		super(id);
		this.name = name;
	}
	@Override
    public String toString() {
        return super.toString() + '(' + name + ')';
    }
}