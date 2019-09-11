/**
 * 提交回复
 */
function post() {
    let questionId = $("#question_id").val();
    let content = $("#comment_content").val();
    comment2target(questionId,1,content);
}

function comment2target(targetId, type, content) {
    if (!content){
        alert("不能回复空内容~~~");
        return;
    }
    $.ajax({
        contentType:"application/json",
        type:"post",
        url:"/comment",
        data:JSON.stringify({
            "parentId":targetId,
            "content":content,
            "type":type
        }),
        success:function (response) {
            if (response.code == 200){
                window.location.reload();
            } else {
                if (response.code == 2003){
                    let isAccepted = confirm(response.message);
                    if (isAccepted){
                        window.open("https://github.com/login/oauth/authorize?client_id=df5aed19fe7df659c65d&redirect_uri=http://localhost:8887/callback&scope=user&state=1");
                        window.localStorage.setItem("closable",true);
                    }
                }
                alert(response.message);
            }
        },
        dataType:"json"
    });
}

function comment(e) {
    let commentId = e.getAttribute("data-id");
    let content = $("#input-"+commentId).val();
    comment2target(commentId,2,content);
}

function dianzan(e) {
    let id = e.getAttribute("data-id");
    let collapse = e.getAttribute("data-collapse");
    if (collapse) {
        $.ajax({
            contentType:"application/json",
            type:"post",
            url:"/dianzan",
            data:JSON.stringify({
                "id":id,
                "likeCount":-1,
            }),
            success:function (response) {
                if (response.code == 200){
                    e.removeAttribute("data-collapse");
                    e.classList.remove("active");
                }
            },
            dataType:"json"
        });
    }else {
        $.ajax({
            contentType:"application/json",
            type:"post",
            url:"/dianzan",
            data:JSON.stringify({
                "id":id,
                "likeCount":1,
            }),
            success:function (response) {
                if (response.code == 200){
                    e.setAttribute("data-collapse","in");
                    e.classList.add("active");
                }
            },
            dataType:"json"
        });
    }
}

/**
 * 展开二级评论
 */
function collapseComments(e) {
    let id = e.getAttribute("data-id");
    let comments = $("#comment-"+id);

    //获取二级评论展开状态
    let collapse = e.getAttribute("data-collapse");
    if (collapse) {
        comments.removeClass("in");
        e.removeAttribute("data-collapse");
        e.classList.remove("active");
    }else {
        let subCommentContainer = $("#comment-"+id);
        if (subCommentContainer.children().length != 1){
            comments.addClass("in");
            e.setAttribute("data-collapse","in");
            e.classList.add("active");
        } else {
            $.getJSON("/comment/"+id, function (data) {
                $.each(data.data.reverse(), function (index, comment) {
                    let mediaLeftElement = $("<div/>",{
                        "class":"media-left"
                    }).append($("<img/>",{
                        "class":"media-object img-rounded",
                        "src":comment.user.avatarUrl
                    }));

                    let mediaBodyElement = $("<div/>",{
                        "class":"media-body"
                    }).append($("<h5/>",{
                        "class":"media-heading",
                        "html":comment.user.name
                    })).append($("<div/>",{
                        "html":comment.content
                    })).append($("<div/>",{
                        "class":"menu"
                    }).append($("<span/>",{
                        "class":"pull-right",
                        html:moment(comment.gmtCreate).format('YYYY-MM-DD')
                    })));

                    let mediaElement = $("<div/>",{
                        "class":"media"
                    }).append(mediaLeftElement)
                        .append(mediaBodyElement);

                    let commentElement = $("<div/>",{
                        "class":"col-lg-12 col-md-12 col-sm-12 col-xs-12 comments"
                    }).append(mediaElement);

                    subCommentContainer.prepend(commentElement);

                });
                comments.addClass("in");
                e.setAttribute("data-collapse","in");
                e.classList.add("active");
            });
        }
    }

}

function selectTag(value) {
    let previous = $("#tag").val();
    if (previous.indexOf(value) == -1){
        if (previous) {
            $("#tag").val(previous + ',' +value);
        }else {
            $("#tag").val(value);
        }
    }
}

function showSelectTag() {
    $("#select-tag").show();
}

/*
function notShowSelectTag() {
    $("#select-tag").hide();
}*/
