package ru.artamonov.crud.util.json;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public final class LocalDateAdapter extends TypeAdapter<LocalDate> {
	@Override
	public void write(final JsonWriter jsonWriter, final LocalDate localDate ) throws IOException {
		jsonWriter.value(localDate.toString());
	}

	@Override
	public LocalDate read( final JsonReader jsonReader ) throws IOException {
		return LocalDate.parse(jsonReader.nextString());
	}
}
