<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="Cache-Control" content="no-cache">

<title>${pageTitle}</title>

<!-- css -->
<link rel="stylesheet" href="resources/css/screen.css" type="text/css"/>
<link rel="stylesheet" href="resources/css/jquery.datepick.css"/>


<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>

<script src="resources/js/table_data_processor.js" type="text/javascript" id="tdp"></script>


<script src="resources/js/jquery.plugin.js"></script>
<script src="resources/js/jquery.datepick.js"></script>

<script>
    $(function() {
        $('#worker_birthday').datepick({dateFormat: 'yyyy-mm-dd'}
        );

        $('#dateFrom').datepick({dateFormat: 'yyyy-mm-dd',
                                        onSelect: function(){
                                            $('#dateTo').val($('#dateFrom').val());
                                        }}
        );

        $('#dateTo').datepick({dateFormat: 'yyyy-mm-dd'}
        );
    });



</script>


<!--  styled select box script version 2 -->
<script src="resources/js/jquery.selectbox-0.5_style_2.js" type="text/javascript"></script>
<script type="text/javascript">
$(document).ready(function() {
	$('.styledselect_form_1').selectbox({ inputClass: "styledselect_form_1" });
	$('.styledselect_form_2').selectbox({ inputClass: "styledselect_form_2" });
});
</script>





