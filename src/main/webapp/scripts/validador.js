/**
 *  validação campos obrigatorios
 * 
 * @author adriel Vinicius
 */


function validar(){
	let nome = formContato.nome.value;
	let fone = formContato.fone.value;
	
	if (nome==="" || nome === null){
		alert("Preencha o campo Nome");
		formContato.nome.focus();
		
		return false;
	}else if(fone==="" || fone === null){
		alert("Preencha o campo Fone");
		formContato.fone.focus();
		return false;
	}else{
		document.forms["formContato"].submit();
	}
	
}