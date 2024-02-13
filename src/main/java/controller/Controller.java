package controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.DAO;
import model.JavaBeans;

@WebServlet(urlPatterns = { "/Controller", "/main","/insert","/select","/update","/delete"})
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
			novoContato(request,response);
			
		}else if(action.equals("/select")) {
			listarContato(request,response);
			
		}else if(action.equals("/update")) {
			editarContato(request,response);
			
		}else if(action.equals("/delete")) {
			removerContato(request,response);
		}else {
			response.sendRedirect("index.html");
		}
		// teste conexao
//		
//		dao.testeConexa();
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
		
		//teste de recebimento da lista
		
//		for (int i = 0; i<lista.size();i++) {
//			System.out.println(lista.get(i).getIdcon());
//			System.out.println(lista.get(i).getNome());
//			System.out.println(lista.get(i).getFone());
//			System.out.println(lista.get(i).getEmail());
//			
//		}
	}
	
	//Novov contato
	protected void novoContato(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//teste de recebimento dos formulario
//		System.out.println(request.getParameter("nome"));
//		System.out.println(request.getParameter("fone"));
//		System.out.println(request.getParameter("email"));
		
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
		
		// Recebimento do id do contato que será editado
		String idcon = request.getParameter("idcon");
		System.out.println(idcon);
		
		// Setar a variável JavaBeans
		contato.setIdcon(idcon);
		
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
		
		//teste de recebimento
//		System.out.println(contato.getIdcon());
//		System.out.println(contato.getNome());
//		System.out.println(contato.getFone());
//		System.out.println(contato.getEmail());
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
		//recebimento do id do contato a ser excluido (confirmador.js)
		String idcon = request.getParameter("idcon");
		//setar a variavel idcon JavaBeans
		contato.setIdcon(idcon);
		
		//executar o método deletarContato(DAO) passando o objjeto contato
		dao.deletarContato(contato);

		// redirecionar para o documento agenda.jsp (atualiozando as alterações)
		response.sendRedirect("main");
	}
}
