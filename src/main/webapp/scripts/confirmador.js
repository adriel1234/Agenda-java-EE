/**
 * Confirmaação de exclusão de um contato
 * 
 * @author Professor José de Assis
 * @param idcon
 */

 function confirmar(idcon){
	 let resposta = confirm("confirmar a exclusão deste contato?");
	 if(resposta === true){
		 window.location.href="delete?idcon="+idcon;
	 }
	 
 }