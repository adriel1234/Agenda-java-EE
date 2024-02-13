/**
 * Confirmaação de exclusão de um contato
 * @author Professor José de Assis
 */

 function confirmar(idcon){
	 let resposta = confirm("confirmar a exclusão deste contato?");
	 if(resposta === true){
		 //alert(idcon);
		 window.location.href="delete?idcon="+idcon;
	 }
	 
 }