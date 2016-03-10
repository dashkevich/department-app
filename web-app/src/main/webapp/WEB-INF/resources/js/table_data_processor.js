
    //страница, на которой сейчас находимся
    found = document.location.href.match(/app\/([a-z]+)[\/]{0,1}/i);
    var modul = found[1];


    //редактирование выбранной записи
    var current_edit_td = null;
    var old_value;
    var edis_sdtatus = true;
    var edit_element_type;

    $(document).ready(function(){
      $('.datatd').dblclick(function(){

          old_value = $(this).text();
          $(this).html("");

            //birthday edit
            if($(this).parent().children("td").get(3) == this && modul == "workers"){

                $(this).append('<input id="edit_td" value="' + old_value + '" />');
                edis_sdtatus = false;
                $('#edit_td').datepick(
                {   dateFormat: 'yyyy-mm-dd',
                    minDate: new Date(1960, 1-12, 01),
                    onSelect: function(){
                        edis_sdtatus = true;
                    }
                });

                edit_element_type = "input";
            }
            else if($(this).parent().children("td").get(2) == this && modul == "workers"){
                $(this).append('<select>' + $(".addform").find("select").html() + '</select>');
                edit_element_type = "select";
            }
            else{
                $(this).append('<textarea style="width: 97%">' + old_value + '</textarea>');
                edit_element_type = "textarea";
            }





          current_edit_td = this;
      });
    });


    //update selected row in the table.
    $(function(){
        $(document).click(function(event) {
            if( $(event.target).closest( edit_element_type ).length ) return;
            if(current_edit_td == null) return;

            if(edis_sdtatus == false) return;

            event.stopPropagation();


            var value = $(current_edit_td).find(edit_element_type).val();


            var departmentValue;
            if(edit_element_type == "select"){
               departmentValue = $(current_edit_td).find(edit_element_type).find(":selected").text();
            }

            if(value.length < 0 || value == old_value){
                $(current_edit_td).html(old_value);
                current_edit_td = null;
                return;
            }

            //получить всю строку в которой редактируется какое-либо поле
            var tds = $(current_edit_td).parent().children();
            var tdValues = [];
            tdValues.push( $(tds[0]).find('input').attr('name') );

            for(i = 1; i < tds.length-1; i++){
                if(tds[i] == current_edit_td){
                    tdValues.push(value);
                }
                else{
                    tdValues.push( $(tds[i] ).text().replace(/\r|\n/g, '').trim() );
                }
            }

            if(edit_element_type != "select"){
                tdValues[2] = $('.addform option').filter(function () { return $(this).html() == tdValues[2]; }).val();
            }

            var data;
            if(modul == "workers"){
                  data = {
                              id: tdValues[0],
                              name: tdValues[1],
                              departmentID: tdValues[2],
                              birthday: tdValues[3],
                              salary: tdValues[4].replace(/\s+/g, '')
                          };
            }

            if(modul == "departments"){
                data = {id: tdValues[0], name: tdValues[1]};
            }

/*
            if( !isValid(data) ){
                $(current_edit_td).html(old_value);
                current_edit_td = null;
                return;
            }
*/

            $.ajax({
                type: "POST",
                url: "/app/" + modul + "/update",
                contentType: "application/json",
                dataType: "json",
                data: JSON.stringify(data),
                success: function(){

                    if(edit_element_type == "select"){
                        $(current_edit_td).html(departmentValue);
                    }else{
                        $(current_edit_td).html(value);
                    }
                    //$(current_edit_td).html(value);
                    current_edit_td = null;
                },
                error: function(jqxhr, textStatus, errorThrown){
                    alert(textStatus);
                    $(current_edit_td).html(old_value);
                    current_edit_td = null;
                }
            });

        });
    });


//add new record =================================================================================

$(document).ready(function(){
    $('.addform').submit(function(eventObject){

 	  var data = $(this).serialize();

        $.ajax({
          type: 'POST',
          url: "/app/" + modul + "/add",
          data: data,
          success: function() {
                location.href = modul;
          },
          error:  function(jqxhr, textStatus, errorThrown){
            alert(textStatus);
          }
        })

        return false;
    });
});

//================================================================================================

//delete selected record =========================================================================

    $(document).ready(function(){
      $('.icon-2').click(function(){
          var deleteID = $(this).parent().parent().find('input').attr('name');


          $.ajax({
              type: "POST",
              url: "/app/" + modul + "/delete",
              data: {id: deleteID},
              success: function()
              {
                location.reload();
              },
              error: function(err){
                  alert('error: ' + err);
              }
          });


          return false;
      });
    });

// ==============================================================================================


//find workers by date
$(document).ready(function(){
    $('.searchform').submit(function(eventObject){

        var data = $(this).serialize();
        var current_href = location.href;

        did = getParameterByName("did");

        //if dateFrom not select;
        if(getParameterByName("dateFrom", "?" + data) == ""){
            data = data.replace("dateFrom=", "dateFrom=1900-01-01");
        }

        if(did != null && did != ''){
            location.search = "?did=" + did + "&" + data;
        }else{
            location.search = "?" + data;
        }

        return false;

    });
});


function getParameterByName(name, url) {

    if (!url) url = window.location.href;
    name = name.replace(/[\[\]]/g, "\\$&");
    var regex = new RegExp("[?&]" + name + "(=([^&#]*)|&|#|$)"),
        results = regex.exec(url);
    if (!results) return null;
    if (!results[2]) return '';
    return decodeURIComponent(results[2].replace(/\+/g, " "));

}

function isValid(data){
      if(modul == "workers"){
              if(data.id < 1){
                  alert("Error in |id|: " + data.id);
                  return false;
              }
              if(data.name.search(/^[a-zA-Zа-яА-Я][a-zA-Zа-яА-Я-' ]+[a-zA-Zа-яА-Я']?$/g) == -1){
                  alert("Invalid name: " + data.name);
                  return false;
              }
              if(data.birthday.search(/[1,2][0-9]{3}-[0-9]{1,2}-[0,1,2,3][0-9]/g) == -1){
                  alert("Invalid birthday: " + data.birthday);
                  return false;
              }
              if(data.departmentID < 0){
                  alert("Invalid departmentID: " + data.departmentID);
                  return false;
              }
              if(data.salary <= 0){
                  alert("Invalid salary: " + data.salary);
                  return false;
              }
      }
      else if(modul == "departments"){
              if(data.name.search(/^[a-zA-Zа-яА-Я][a-zA-Zа-яА-Я-' ]+[a-zA-Zа-яА-Я']?$/g) == -1){
                  alert("Invalid name: " + data.name);
                  return false;
              }
      }

      return true;
}
