var Portal = Portal || {}

Portal.EditorTexto = (function(){
	
	function EditorTexto(){
		this.btnSubmit = $('.js-btn-submit');
		this.fieldDescricao = $('input[name=descricao]');
		this.fieldDescricaoHtml = $('input[name=descricaoHtml]');
		this.fieldHorario = $('input[name=horario]');
		this.fieldHorarioHtml = $('input[name=horarioHtml]');
		this.theme = 'snow';
		this.editorDescricao = '.descricao';
		this.editorHorario = '.horario';
		this.optionsFullTollbar = [
	        [{ 'font': [] }, { 'size': [] }],
	        [ 'bold', 'italic', 'underline', 'strike' ],
	        [{ 'color': [] }, { 'background': [] }],
	        [{ 'script': 'super' }, { 'script': 'sub' }],
	        [{ 'header': '1' }, { 'header': '2' }, 'blockquote', 'code-block' ],
	        [{ 'list': 'ordered' }, { 'list': 'bullet'}, { 'indent': '-1' }, { 'indent': '+1' }],
	        [ {'direction': 'rtl'}, { 'align': [] }],
	        [ 'link', 'image', 'video', 'formula' ],
	        [ 'clean' ]
	      ];
		
		this.optionsTollbar = [
	        [{ 'font': [] }, { 'size': [] }],
	        [ 'bold', 'italic', 'underline', 'strike' ],
	        [{ 'color': [] }, { 'background': [] }],
	        [{ 'script': 'super' }, { 'script': 'sub' }],
	        [{ 'header': '1' }, { 'header': '2' }, 'blockquote'],
	        [{ 'list': 'ordered' }, { 'list': 'bullet'}, { 'indent': '-1' }, { 'indent': '+1' }],
	        [ {'direction': 'rtl'}, { 'align': [] }],
	        [ 'link'],
	        [ 'clean' ]
	      ];
	}
	
	EditorTexto.prototype.enable = function(){
		
		var fullEditor = new Quill(this.editorDescricao, {
		    bounds: this.editorDescricao,
		    modules: {
		      syntax: true,
		      toolbar: this.optionsTollbar,
		    },
		    theme: this.theme
		  });
		
		var fullEditor2 = new Quill(this.editorHorario, {
		    bounds: this.editorHorario,
		    modules: {
		      syntax: true,
		      toolbar: this.optionsTollbar,
		    },
		    theme: this.theme
		  });
	
		if(this.fieldDescricao.val()){
			fullEditor.setContents(JSON.parse(this.fieldDescricao.val()), 'api');
		}
		
		if(this.fieldHorario.val()){
			fullEditor2.setContents(JSON.parse(this.fieldHorario.val()), 'api');
		}
		
		this.btnSubmit.on('click', putText.bind(this, fullEditor, fullEditor2));
	}
	
	function putText(text, text2){
		this.fieldDescricao.val(JSON.stringify(text.getContents()));
		this.fieldDescricaoHtml.val(text.container.firstChild.innerHTML);
		
		this.fieldHorario.val(JSON.stringify(text2.getContents()));
		this.fieldHorarioHtml.val(text2.container.firstChild.innerHTML);
	}
	
	return EditorTexto;
}());

$(function(){
	
	var editorTexto = new Portal.EditorTexto();
	editorTexto.enable();
	
});