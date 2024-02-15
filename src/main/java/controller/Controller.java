package controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import model.DAO;
import model.JavaBeans;

@WebServlet(urlPatterns = { "/Controller", "/main","/insert","/select","/update","/delete","/report"})
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;
	DAO dao = new DAO();
	JavaBeans contato = new JavaBeans();

	public Controller() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getServletPath();
		System.out.println(action);

		if (action.equals("/main")) {
			contatos(request, response);

		}else if(action.equals("/insert")) {
			adicionarContato(request,response);
			
		}else if(action.equals("/select")) {
			listarContato(request,response);
			
		}else if(action.equals("/update")) {
			editarContato(request,response);
			
		}else if(action.equals("/delete")) {
			removerContato(request,response);
		}else if(action.equals("/report")) {
			gerarRelatorio(request,response);
		}else {
			response.sendRedirect("index.html");
		}

	}

	// Listar Contatos
	protected void contatos(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		// Criando um objeto que irá receber os dados JavaBeans
		
		ArrayList<JavaBeans> lista = dao.listarContatos();
		
		//Encaminha a lista ao documento agenda.jsp
		
		request.setAttribute("contatos", lista);
		RequestDispatcher rd = request.getRequestDispatcher("agenda.jsp");
		rd.forward(request, response);
		
	}
	
	//Novov contato
	protected void adicionarContato(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// setar as variáveis JavaBeans
		contato.setNome(request.getParameter("nome"));
		contato.setFone(request.getParameter("fone"));
		contato.setEmail(request.getParameter("email"));
		
		//Invocar  o método inserirContatos passando o objeto contato
		dao.inserirContato(contato);
		
		// redirencionar para o documento agenda.jsp
		response.sendRedirect("main");
		
	}
	
	//Editar contato
	protected void listarContato(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		// Setar a variável JavaBeans
		contato.setIdcon(request.getParameter("idcon"));
		
		//Executar o método selecionarContato (DAO)
		dao.selecionarContato(contato);
		
		// Setar os atributos do formulario com o conteudo JavaBeans
		request.setAttribute("idcon", contato.getIdcon());
		request.setAttribute("nome", contato.getNome());
		request.setAttribute("fone", contato.getFone());
		request.setAttribute("email", contato.getEmail());
		
		//Encaminhar ao documento editar.jsp
		RequestDispatcher rd = request.getRequestDispatcher("editar.jsp");
		rd.forward(request, response);
	}
	protected void editarContato(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		// setar as variaveis JavaBeans
		contato.setIdcon(request.getParameter("idcon"));
		contato.setNome(request.getParameter("nome"));
		contato.setFone(request.getParameter("fone"));
		contato.setEmail(request.getParameter("email"));
		
		// executar o método alterarContato
		dao.alterarContato(contato);
		
		//redirecionar para o documento agenda.jsp (atualiozando as alterações)
		response.sendRedirect("main");
		
	}
	
	//Remover Contato
	protected void removerContato(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		//setar a variavel idcon JavaBeans
		contato.setIdcon(request.getParameter("idcon"));
		
		//executar o método deletarContato(DAO) passando o objjeto contato
		dao.deletarContato(contato);

		// redirecionar para o documento agenda.jsp (atualiozando as alterações)
		response.sendRedirect("main");
	}
	
	// Gerar relatório em PDF
	protected void gerarRelatorio(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Document documento = new Document();
		
		try {
			//tipo de conteudo
			response.setContentType("appliication/pdf");
			
			//nome do documento
			response.addHeader("Content-Disposition", "inline; filename=contatos.pdf");
			//criar o documento
			PdfWriter.getInstance(documento, response.getOutputStream());
			
			//abrir o documento -> conteúdo
			documento.open();
			documento.add(new Paragraph("Lista de contatos: "));
			documento.add(new Paragraph(" "));
			//criar uma tabela
			PdfPTable tabela = new PdfPTable(3);
			//cabeçalho
			PdfPCell col1 = new PdfPCell(new Paragraph("Nome"));
			PdfPCell col2 = new PdfPCell(new Paragraph("Fone"));
			PdfPCell col3 = new PdfPCell(new Paragraph("E-mail"));
			
			tabela.addCell(col1);
			tabela.addCell(col2);
			tabela.addCell(col3);
			
			// popular a tabela com os contatos
			ArrayList<JavaBeans> lista = dao.listarContatos();
			for (JavaBeans contato : lista) {
				tabela.addCell(contato.getNome());
				tabela.addCell(contato.getFone());
				tabela.addCell(contato.getEmail());
				
			}
			documento.add(tabela);
			documento.close();

		} catch (Exception e) {
			e.printStackTrace();
			documento.close();
		}
		
	}
}
