package ru.artamonov.crud.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.artamonov.crud.annotation.Required;
import ru.artamonov.crud.util.exception.BadRequestException;
import ru.artamonov.crud.util.json.LocalDateAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.time.LocalDate;

public final class ServletUtil {
	private static final Gson gson = new GsonBuilder()
			.disableHtmlEscaping()
			.registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
			.create();


	public static <T> T expect(Class<T> Class, HttpServletRequest request) throws BadRequestException {
		try {
			T dto = gson.fromJson(request.getReader(), Class);

			Field[] fields = dto.getClass().getDeclaredFields();
			for (Field f : fields) {
				if (f.getAnnotation(Required.class) != null) {
					f.setAccessible(true);
					if (f.get(dto) == null) {
						throw new BadRequestException("Missing field in JSON: " + f.getName());
					}
				}
			}
			return dto;

		} catch (IOException | IllegalAccessException e) {
			throw new BadRequestException("Invalid request payload");
		}
	}

	public static void respond(Object payload, HttpServletResponse response) throws IOException {
		response.setContentType("text/json");
		PrintWriter out = response.getWriter();
		out.println(gson.toJson(payload));
	}

	public static void setEncodingAndContentType(HttpServletResponse resp) {
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("application/json");
	}

}
