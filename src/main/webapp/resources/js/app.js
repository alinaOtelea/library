$(document).ready(function(){
    $('.datepicker').pickadate({
        format:"yyyy-mm-dd",
        selectDays: true,
        selectMonths: true,
        // min: [1800,1,1],
        // max: new Date(),
        closeOnSelect: true,
        closeOnClear: true,
        selectYears: 70
    });

    $('#years').material_select();

    $(".select-dropdown").click(function(){
        var max = new Date().getFullYear(),
            min = max - 20,
            options = '';
        for (var i = max; i > min; i--) {
            options += '<option value="' + i+ '">' + i+ '</option>';
        }
        $("#years").append(options).material_select();
    });

    $(".modal-close").on("click",function(){
        $("#eroare").html("");
        clearModalInputs();
        $('.jsgrid-cancel-edit-button').click();
    });

    //initializare modal
    $('.modal').modal();

    //reinitializare labels
    Materialize.updateTextFields();

    function clearModalInputs(){
        $('#modal-cnpCititor').val("");
    }

    function todayDate(){
        var today = new Date();
        var day = today.getDate();
        var month = today.getMonth()+1;
        var year = today.getFullYear();

        var todayString = year+"-"+month+"-"+day;
        $('#modal-data-imprumut').val(todayString).addClass("valid");
        $("#label-modal-imprumut").addClass("active");
    }

    function returnDate(){
        var weekDay = new Date(+new Date + 12096e5);
        var rDay = weekDay.getDate();
        var rMonth = weekDay.getMonth()+1;
        var rYear = weekDay.getFullYear();

        var returnString = rYear+"-"+rMonth+"-"+rDay;
        $('#modal-data-retur').val(returnString).addClass("valid");
        $("#label-modal-retur").addClass("active");
    }

    function getFilterData() {
        var elements = $("#formCarti").find("input[type=text],input[type=number]");
        var jsonValues = {};
        for (var i = 0; i < elements.length; i++) {
            $input = $(elements[i]);
            if ($input.attr('name') && ($input.val() != "")) {
                jsonValues[$input.attr('name')] = $input.val();
            }
        }
        return jsonValues;
    }

    function isEmpty(obj) {
        for(var prop in obj) {
            if(obj.hasOwnProperty(prop))
                return false;
        }
        return JSON.stringify(obj) === JSON.stringify({});
    }

    function applyFilter(value) {
        var filterData = getFilterData();
        var result = false;
        for(var key in value) {
            if(value[key] === filterData[key]) {
                result = true;
            }
        }
        return result;
    }

    function ajax(id) {
        var result = $.ajax({
            type: "GET",
            url: "restImprumut/count/" + id,
            data: String,
            async: false,
            success: function (item) {
                // valItem.push(Number(item));
            }
        }).responseText;
        return Number(result);
    }

    /* Cauta CARTE tabel */
    var jsGrid = $('#jsGridCarte');
    var firstRun = true;

    jsGrid.jsGrid({
        width: "100%",
        height: "600px",
        inserting: true,
        editing: true,
        sorting: true,
        paging: true,
        autoload: true,
        filterable:true,
        pageSize: 10,
        pageButtonCount: 5,
        pagePrevText: "Prev",
        pageNextText: "Next",
        deleteConfirm: "Doriti sa stergeti cartea?",


        controller: {
            filterValue: function (row) {
                console.log(row);
            },
            loadData: function(filter) {
                // var data = $.Deferred();
                // $.ajax({
                //     type: "GET",
                //     contentType: "application/json; charset=utf-8",
                //     url: "rest/all",
                //     dataType: "json"
                // }).done(function(response){
                //     data.resolve(response);
                // });

                var finalResult;
                // var valuesCarte = $("#jsGridCarte").jsGrid("option", "data");
                var checkData = getFilterData();
                if (!firstRun) {
                    finalResult = tableValuesCarte.filter(applyFilter);

                    if(isEmpty(checkData)){
                        return tableValuesCarte;
                    } else{
                        return finalResult;
                    }

                } else {
                    firstRun = false;
                    var d = $.Deferred();
                    $.ajax({
                        type: "GET",
                        url: "rest/all",
                        data: filter,
                        dataType: "json"
                    }).done(function(result) {
                        tableValuesCarte = result;
                        d.resolve(result);
                    });
                    return d.promise();
                }
            },

            insertItem: function(item) {
                return $.ajax({
                    type: "POST",
                    url: "rest/carte",
                    data: JSON.stringify(item),
                    contentType: "application/json;charset=utf-8",
                    dataType: "json"
                });
            },

            updateItem: function(item) {
                return $.ajax({
                    type: "PUT",
                    url: "rest/update/"+ item.idCarte +"/?autor=" + item.autor + "&titlu="+item.titlu+"&domeniu="+item.domeniu+"&stoc="+item.stoc+"&editura="+item.editura+"&data_publicare="+item.data_publicare+"&isbn="+item.isbn+"&disponibile="+item.disponibile,
                    data: JSON.stringify(item),
                    contentType: "application/json;charset=utf-8",
                    dataType: "json"
                })
            },

            deleteItem: function(item) {
                return $.ajax({
                    type: "DELETE",
                    url: "rest/remove/" + item.idCarte ,
                    data: item
                });
            },
        },

        fields: [
            {
                itemTemplate: function(value, item) {
                    if (item.disponibile <= 0) {
                        return $("<p>").text("Indisponibila")
                    } else {
                        return $("<a>").text("Imprumuta").addClass("imprumuta padding-0 waves-effect waves-light btn").attr("href", "#modal1")
                        .on("click", function() {
                            $("#modal-idCarte").val(item.idCarte).addClass("valid");
                            $("#label-modal-idCarte").addClass("active");

                            todayDate();
                            returnDate();
                        });
                    }
                }
            },
            { name: "idCarte"},
            { name: "titlu", type: "text", validate: "required"},
            { name: "autor", type: "text", validate: "required"},
            { name: "editura", type: "text", validate: "required"},
            { name: "domeniu", type: "text", validate: "required"},
            {
                name: "stoc",
                type: "number" ,
                validate:
                    [
                        "required",
                        { validator: "min",
                            param: 0,
                            message: "Va rugam sa introduceti la stoc un numar mai mare ca 0"
                        }
                    ]
            },
            {
                name: "data_publicare",
                type: "number",
                validate:
                    [
                        "required",
                        { validator: "range",
                            param: [1700,new Date().getFullYear()],
                            message: "Anul publicarii nu poate fi mai mic de 1700 si mai mare decat anul curent!"
                        }
                    ]
            },
            {
                name: "isbn",
                type: "text",
                validate:
                [
                    "required",
                    { validator:
                        function(value, thisRow) {
                            var result = false;
                           function filteredValues (rows) {
                               var idCarteCurrent = thisRow.idCarte;
                               if ( (rows.isbn === value) && (rows.idCarte !== idCarteCurrent)){
                                   result = true ;
                               }
                               return result;
                           }
                            var filteredValuesArray = tableValuesCarte.filter(filteredValues);
                            if(filteredValuesArray.length) {
                                return false;
                            }
                            return true;
                        },
                        message: "Acest isbn se mai afla in baza de date"
                    }
                ]
            },
            { name: "disponibile", editing: false
                // itemTemplate(value,row){
                //     var count = ajax(row.idCarte);
                //     var diff = row.stoc - count;
                    // return diff;
                // }
            },
            { type: "control" }
        ]
    });

    $('#cautaCarte').click(function(){
        $("#jsGridCarte").jsGrid('loadData');
    });


    /*
     -----------------------------------------------------------------------------------------------------
                                          CITITOR
     */

    var jsGridCititor = $('#jsGridCititor');
    var firstRun1 = true;

    jsGridCititor.jsGrid({
        width: "100%",
        height: "600px",
        inserting: true,
        editing: true,
        sorting: true,
        paging: true,
        autoload: true,
        pageSize: 10,
        pageButtonCount: 5,
        deleteConfirm: "Doriti sa stergeti cititorul?",

        controller: {
            filterValue: function (row) {
                console.log(row);
            },
            loadData: function(filter) {
                filter.nume = $('#input_nume').val();
                if (!firstRun1) {
                    finalResult1 = tableValues1.filter(function (item) {
                        if (filter.nume && item.nume !== filter.nume) {
                            return false;
                        } else {
                            return true;
                        }
                    });
                    return finalResult1;
                } else {
                    firstRun1 = false;
                    var d = $.Deferred();
                    $.ajax({
                        type: "GET",
                        url: "restCititor/allCititor",
                        data: filter,
                        dataType: "json"
                    }).done(function(result) {
                        tableValues1 = result;
                        d.resolve(result);
                    });
                    return d.promise();
                }
            },

            insertItem: function(item) {
                return $.ajax({
                    type: "POST",
                    url: "restCititor/addNewCititor/?idCititor=NULL&cnp="+item.cnp+"&nume=" + item.nume + "&prenume="+item.prenume+"&adresa="+item.adresa+"&telefon="+item.telefon+"&email="+item.email+"&data_nasterii="+item.data_nasterii+"&data_inregistrare="+item.data_inregistrare,
                    data: item
                }).done(function (data) {
                    item.idCititor = data;
                    tableValues1.push(item);
                    jsGridCititor.jsGrid('loadData');
                });
            },

            updateItem: function(item) {
                return $.ajax({
                    type: "PUT",
                    url: "restCititor/updateCititor/"+ item.idCititor +"/?cnp="+item.cnp+"&nume=" + item.nume + "&prenume="+item.prenume+"&adresa="+item.adresa+"&telefon="+item.telefon+"&email="+item.email+"&data_nasterii="+item.data_nasterii+"&data_inregistrare="+item.data_inregistrare,
                    data: item
                });
            },

            deleteItem: function(item) {
                return $.ajax({
                    type: "DELETE",
                    url: "restCititor/removeCititor/" + item.idCititor ,
                    data: item
                });
            },
        },

        fields: [
            { name: "idCititor"},
            { name: "cnp", type: "text"},
            { name: "nume", type: "text"},
            { name: "prenume", type: "text"},
            { name: "adresa", type: "text"},
            { name: "telefon", type: "text"},
            { name: "email", type: "text" },
            { name: "data_nasterii", type: "text"},
            { name: "data_inregistrare", type: "text"},
            { type: "control" }
        ]
    });


    $('#cautaCititor').click(function(){
        $("#jsGridCititor").jsGrid("loadData").done(function(){

        });
    });

    /*
    -----------------------------------------------------------------------------------------------------
                                        Imprumut tabel
     */

    function applyFilterImprumut(value) {
        var filterData = getFilterImprumutData();
        var result = false;
        for(var key in value) {
            if(value[key].toString() === filterData[key]) {
                result = true;
            }
        }
        return result;
    }

    function getFilterImprumutData() {
        var elements2 = $("#formImprumut").find("input");
        var jsonValuesImprumut = {};
        for (var i = 0; i < elements2.length; i++) {
            $input = $(elements2[i]);
            if ($input.attr('name') != "returnata") {
                jsonValuesImprumut[$input.attr('name')] = $input.val();
            }
            else if($input.attr('name') == "returnata"){
                if($('#returnata').is(':checked')){
                    jsonValuesImprumut[$input.attr('name')] = "1";
                } else {
                    jsonValuesImprumut[$input.attr('name')] = "0";
                }
            }
        }

        return jsonValuesImprumut;
    }

    var jsGridImprumut = $('#jsGridImprumut');
    var firstRunImprumut = true;

    jsGridImprumut.jsGrid({
        width: "100%",
        inserting: false,
        deleting: false,
        editing: true,
        sorting: true,
        paging: true,
        autoload: true,
        filterable:true,
        pageSize: 13,
        pageButtonCount: 5,
        pagePrevText: "Prev",
        pageNextText: "Next",

        controller: {
            filterValue: function (row) {
                console.log(row);
            },
            loadData: function(filter) {
                var finalResultImprumut;
                if (!firstRunImprumut) {
                    finalResultImprumut = tableValuesImprumut.filter(applyFilterImprumut);

                    return finalResultImprumut;
                } else {
                    firstRunImprumut = false;
                    var d = $.Deferred();
                    $.ajax({
                        type: "GET",
                        url: "restImprumut/allImprumut",
                        data: filter,
                        dataType: "json"
                    }).done(function(result) {
                        tableValuesImprumut = result;
                        d.resolve(result);
                    });
                    return d.promise();
                }
            },

            insertItem: function(item) {
                return $.ajax({
                    type: "POST",
                    url: "restImprumut/addNewImprumut",
                    data: JSON.stringify(item),
                    contentType: "application/json;charset=utf-8",
                    dataType: "json"
                });
            },

            updateItem: function(item) {
                var returnata;
                if(item.returnata == true){
                    returnata = 1;
                }
                else{
                    returnata = 0;
                }

                return $.ajax({
                    type: "PUT",
                    url: "restImprumut/updateImprumut/"+ item.idImprumut +"/?idCititor="+item.idCititor+"&idCarte=" + item.idCarte + "&data_imprumut="+item.data_imprumut+"&data_retur="+item.data_retur+"&returnata="+returnata,
                    data: item
                }).done(function(){
                    $("#jsGridCarte").jsGrid('loadData').done(function(obj){
                        for (var key in obj){
                            var row = obj[key];
                            var count = ajax(row.idCarte);
                            var diff = row.stoc - count;
                            $("#jsGridCarte").jsGrid("updateItem", {
                                idCarte: row.idCarte,
                                autor: row.autor,
                                titlu: row.titlu,
                                editura: row.editura,
                                domeniu: row.domeniu,
                                stoc: row.stoc,
                                data_publicare: row.data_publicare,
                                isbn: row.isbn,
                                disponibile: diff
                            }).done(function(e){
                                firstRun = true;
                                $("#jsGridCarte").jsGrid('loadData');
                            })
                        }

                    })
                });
            },

            // deleteItem: function(item) {
            //     console.log(item);
            //     return $.ajax({
            //         type: "DELETE",
            //         url: "restCititor/removeCititor/" + item.idCititor ,
            //         data: item
            //     });
            // },
        },

        fields: [
            {
                itemTemplate: function(value, item) {
                    var today = new Date();
                    var retur = new Date(item.data_retur);
                    var diff = Math.floor((retur - today) / (1000*60*60*24));

                    if(item.returnata == 1){
                        return $("<p>").text("Returnata");
                    } else{
                        if (diff > 5){
                            return $("<p>").text("au ramas "+diff+" zile").addClass("green-text");
                        } else if (diff > 0){
                            return $("<p>").text("au ramas "+diff+" zile").addClass("middle");
                        } else{
                            return $("<p>").text("! intarziere de "+Math.abs(diff)+" zile").addClass("eroare");
                        }
                    }
                }
            },
            { name: "idImprumut"},
            { name: "idCititor"},
            { name: "idCarte"},
            { name: "data_imprumut", type: "text"},
            { name: "data_retur", type: "text"},
            { name: "returnata", type: "checkbox", title: "Este returnata",
                itemTemplate: function(value, item) {
                    return $("<input>").attr("type", "checkbox")
                        .attr("checked", value || item.Checked)
                        .on("change", function () {
                            item.Checked = $(this).is(":checked");
                        });
                }
            },
            { type: "control" }
        ]
    });


    $('#cautaImprumut').click(function(){
        $("#jsGridImprumut").jsGrid("loadData");
    });


    $('#imprumutaCarte').click(function(){
        $(".jsgrid-cancel-edit-button").click();
        var valImprumut = $('#modal-data-imprumut').val(),
            valCititor  = $('#modal-cnpCititor').val(),
            valCarte    = $('#modal-idCarte').val(),
            valRetur    = $('#modal-data-retur').val();

        var returDate = new Date(valRetur);
        var imprumutDate = new Date(valImprumut);

        $.ajax({
            type: "GET",
            url: "restCititor/cnp/"+valCititor+"/",
            dataType: "json"
        }).done(function(response) {
            $("#jsGridImprumut").jsGrid("insertItem", {
                idCititor: response,
                idCarte: parseInt(valCarte),
                data_imprumut: valImprumut,
                data_retur: valRetur,
                returnata: 0
            }).done(function (data) {
                $('.success').removeClass('no-display').fadeOut(3000);
                $('.modal-close').click();
                clearModalInputs();
                $("#eroare").html("");

                $("#jsGridCarte").jsGrid('loadData').done(function(obj){
                    for (var key in obj){
                        var row = obj[key];
                        var count = ajax(row.idCarte);
                        var diff = row.stoc - count;
                        $("#jsGridCarte").jsGrid("updateItem", {
                            idCarte: row.idCarte,
                            autor: row.autor,
                            titlu: row.titlu,
                            editura: row.editura,
                            domeniu: row.domeniu,
                            stoc: row.stoc,
                            data_publicare: row.data_publicare,
                            isbn: row.isbn,
                            disponibile: diff
                        }).done(function(e){
                            firstRun = true;
                            $("#jsGridCarte").jsGrid('loadData');
                        })
                    }

                })
            });
        });

        // if( valImprumut === "" || valCititor === "" || valCarte === "" || valRetur === ""  ){
        //      $("#eroare").html("Completati toate campurile !");
        // }
        // else if(returDate <= imprumutDate ){
        //     $("#eroare").html("Data returului nu poate fi mai mica decat data de imprumut !!");
        // }
        // else {
        //     $("#jsGridImprumut").jsGrid("insertItem").done(function(){
        //         console.log("inserat");
        //     });
        // }
    });
});

