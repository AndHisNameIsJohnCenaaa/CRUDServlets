package ru.artamonov.crud.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.artamonov.crud.model.Company;
import ru.artamonov.crud.repository.CompanyRepository;
import ru.artamonov.crud.repository.jdbc.JdbcCompanyRepository;
import ru.artamonov.crud.util.ServletUtil;
import ru.artamonov.crud.util.exception.BadRequestException;
import ru.artamonov.crud.util.exception.ErrorResource;
import ru.artamonov.crud.util.exception.NotFoundException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/companies/*")
public class CompanyController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final Logger logger = LoggerFactory.getLogger(CompanyController.class);
	private CompanyRepository repository;

	@Override
	public void init() throws ServletException {
		repository = JdbcCompanyRepository.getInstance();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ServletUtil.setEncodingAndContentType(resp);
		String pathInfo = req.getPathInfo();
		if (pathInfo == null) {
			List<Company> companies = repository.getAll();
			logger.info("GET companies: {}", companies);
			ServletUtil.respond(companies, resp);
		}
		else {
			int id = Integer.parseInt(pathInfo.substring(1));
			try {
				Company company = repository.get(id);
				logger.info("GET company: {}", company);
				if (company == null) {
					throw new NotFoundException("Company not found");
				}
				ServletUtil.respond(company, resp);
			} catch (NotFoundException e) {
				resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
				ErrorResource error = new ErrorResource("Not found", e.getMessage(), HttpServletResponse.SC_NOT_FOUND);
				ServletUtil.respond(error, resp);
				logger.info("Not found company with id: {}", id);
			}
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ServletUtil.setEncodingAndContentType(resp);
		try {
			Company company = ServletUtil.expect(Company.class, req);
			ServletUtil.respond(repository.save(company), resp);
			logger.info("SAVE company: {}", company);
		} catch (BadRequestException e) {
			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			ErrorResource error = new ErrorResource("Bad Request", e.getMessage(), HttpServletResponse.SC_BAD_REQUEST);
			ServletUtil.respond(error, resp);
			logger.info("SAVE bad Request: {}", e.getMessage());
		}
	}

	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ServletUtil.setEncodingAndContentType(resp);
		try {
			String pathInfo = req.getPathInfo();
			if (pathInfo == null) {
				throw new BadRequestException("No company id was found");
			}else {
				Company company = ServletUtil.expect(Company.class, req);
				company.setId(Integer.parseInt(pathInfo.substring(1)));
				if(!repository.update(company)) {
					throw new NotFoundException("Company not found with id: " + company.getId());
				}
				logger.info("UPDATE company: {}", company);
			}
			ServletUtil.respond(HttpServletResponse.SC_NO_CONTENT, resp);

		} catch (BadRequestException e) {
			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			ErrorResource error = new ErrorResource("Bad Request", e.getMessage(), HttpServletResponse.SC_BAD_REQUEST);
			ServletUtil.respond(error, resp);
			logger.info("UPDATE bad Request: {}", e.getMessage());
		} catch (NotFoundException e) {
			resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
			ErrorResource error = new ErrorResource("Not found", e.getMessage(), HttpServletResponse.SC_NOT_FOUND);
			ServletUtil.respond(error, resp);
			logger.info("UPDATE not found: {}", e.getMessage());
		}
	}

	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			String pathInfo = req.getPathInfo();
			if (pathInfo == null) {
				throw new BadRequestException("No company id was found");
			}else {
				int id = Integer.parseInt(pathInfo.substring(1));
				if (!repository.delete(id)) {
					throw new NotFoundException("Company not found");
				}
				logger.info("DELETE company with id: {}", id);
			}
		} catch (BadRequestException e) {
			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			ErrorResource error = new ErrorResource("Bad Request", e.getMessage(), HttpServletResponse.SC_BAD_REQUEST);
			ServletUtil.respond(error, resp);
			logger.info("DELETE bad Request: {}", e.getMessage());
		} catch (NotFoundException e) {
			resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
			ErrorResource error = new ErrorResource("Not found", e.getMessage(), HttpServletResponse.SC_NOT_FOUND);
			ServletUtil.respond(error, resp);
			logger.info("DELETE not found: {}", e.getMessage());
		}
	}
}
