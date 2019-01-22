
$(function () {
    $('#btSearch').click(function () {
        var tbody = window.document.getElementById("table_body");

        $.ajax({
            type: "get",
            dataType: "json",
            contentType: "application/json;charset=utf-8",
            url: "http://117.50.60.55:8020/thirdCompany/query",
            success: function (msg) {
                    var str = "";
                    var data = msg.data;
                    for (i in data) {
                        str += "<tr class= 'info'>" +
                            "<td align='center'>" + data[i].id + "</td>" +
                            "<td align='center'>" + data[i].appid + "</td>" +
                            "<td align='center'>" + data[i].publicKey + "</td>" +
                            "<td align='center'>" + data[i].privateKey + "</td>" +
                            "<td align='center'>" + data[i].status + "</td>" +
                            "<td align='center'>" + data[i].name + "</td>" +
                            "<td align='center'>" + data[i].callBackUrl + "</td>" +
                            "<td align='center'>" + data[i].createDate + "</td>" +
                            "<td align='center'>" + data[i].updateDate + "</td>" +
                            "</tr>";
                    }
                    tbody.innerHTML = str;
            },
            error: function () {
                alert("查询失败")
            }
        });
    });



    $('#submit').click(function () {
        var tbody = window.document.getElementById("table_body");

        var appid = $("#inputAppId").val();
        var inputName = $("#inputName").val();
        var callBackUrl = $("#callBackUrl").val();

        $.ajax({
            type: "post",
            dataType: "json",
            contentType: "application/json;charset=utf-8",
            url: "http://117.50.60.55:8020/thirdCompany/create",
            data:JSON.stringify({"appid": appid, "name": inputName, "callBackUrl": callBackUrl}),
            success: function (msg) {
                alert("成功")
                tbody.innerHTML = str;
            },
            error: function () {
                alert("失败")
            }
        });
    });



});
