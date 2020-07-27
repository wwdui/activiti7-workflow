/* 分页 */
function goPage(pno) {
    var itable = $('.layui-table-body>.layui-table');
    var num = itable[0].rows.length;
    var totalPage = 0;
    var pageSize = 10;
    if (num / pageSize > parseInt(num / pageSize)) {
        totalPage = parseInt(num / pageSize) + 1;
    } else {
        totalPage = parseInt(num / pageSize);
    }
    var currentPage = pno;
    var startRow = (currentPage - 1) * pageSize + 1;
    var endRow = currentPage * pageSize;
    endRow = (endRow > num) ? num : endRow;
    // console.log(itable[0].rows,"列表表格")
    for (var i = 1; i < (num + 1); i++) {
        var irow = itable[0].rows[i - 1];
        if (i >= startRow && i <= endRow) {
            irow.style.display = "table-row";
        } else {
            irow.style.display = "none";
        }
    }
    var pageEnd = document.getElementById("pageEnd");
    var tempStr = "", content = "", aaa = "...";
    if (currentPage > 1) {
        tempStr += "<span class='btn firstPage' id='firstPage' href=\"#\" onClick=\"goPage(" + (1) + ")\">首页</span>";
        tempStr += "<span class='btn prePage'   id='prePage'   href=\"#\" onClick=\"goPage(" + (currentPage - 1) + ")\">上一页</span>"
    } else {
        tempStr += "<span class='btn firstPage'id='firstPage'>首页</span>";
        tempStr += "<span class='btn prePage' id='prePage'>上一页</span>";
    }

    //总页数大于6时候
    if(totalPage > 6) {
        //当前页数小于5时显示省略号
        if(currentPage < 5) {
            for(var i = 1; i < 6; i++) {
                if(currentPage == i) {
                    content += "<a class='current' onclick=\"goPage("+i+")\">" + i + "</a>";
                } else {
                    content += "<a onclick=\"goPage("+i+")\">" + i + "</a>";
                }
            }
            content += ". . .";
            content += "<a onclick=\"goPage("+totalPage+")\">"+totalPage+"</a>";
        } else {
            //判断页码在末尾的时候
            if(currentPage < totalPage - 3) {
                for(var i = currentPage - 2; i < currentPage + 3; i++) {
                    if(currentPage == i) {
                        content += "<a class='current' onclick=\"goPage("+i+")\">" + i + "</a>";
                    } else {
                        content += "<a onclick=\"goPage("+i+")\">" + i + "</a>";
                    }
                }
                content += ". . .";
                content += "<a onclick=\"goPage("+totalPage+")\">"+totalPage+"</a>";
                //页码在中间部分时候
            } else {
                content += "<a onclick=\"goPage(1)\">1</a>";
                content += ". . .";
                for(var i = totalPage - 4; i < totalPage + 1; i++) {
                    if(currentPage == i) {
                        content += "<a class='current' onclick=\"goPage("+i+")\">" + i + "</a>";
                    } else {
                        content += "<a onclick=\"goPage("+i+")\">" + i + "</a>";
                    }
                }
            }
        }
        //页面总数小于6的时候
    } else {
        for(var i = 1; i < totalPage + 1; i++) {
            if(currentPage == i) {
                content += "<a class='current' onclick=\"goPage("+i+")\">" + i + "</a>";
            } else {
                content += "<a onclick=\"goPage("+i+")\">" + i + "</a>";
            }
        }
    }
    tempStr+=content;

    if(currentPage<totalPage){
        tempStr += "<span class='btn nextPage' id='nextPage' href=\"#\" onClick=\"goPage("+(currentPage+1)+")\">下一页</span>";
        tempStr += "<span class='btn lastPage' id='lastPage' href=\"#\" onClick=\"goPage("+(totalPage)+")\">尾页</span>";
    }else{
        tempStr += "<span class='btn nextPage' id='nextPage'>下一页</span>";
        tempStr += "<span class='btn lastPage' id='lastPage'>尾页</span>";
    }
    tempStr+='<i class="icon_zy iconfont icon-icon-arrow-right2" style="font-size: 14px;"><span class="totalPages">共<span>'+totalPage+'</span>页</span>,<span class="totalSize">共<span>'+num+'</span>条记录</span></i>';
    document.getElementById("barcon").innerHTML = tempStr;

}