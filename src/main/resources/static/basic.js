// 미리 작성된 영역 - 수정하지 않으셔도 됩니다.
// 사용자가 내용을 올바르게 입력하였는지 확인합니다.
function isValidContents(contents) {
    if (contents == '') {
        alert('내용을 입력해주세요');
        return false;
    }
    if (contents.trim().length > 140) {
        alert('공백 포함 140자 이하로 입력해주세요');
        return false;
    }
    return true;
}

// 익명의 username을 만듭니다.
function genRandomName(length) {
    let result = '';
    let characters = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';
    let charactersLength = characters.length;
    for (let i = 0; i < length; i++) {
        let number = Math.random() * charactersLength;
        let index = Math.floor(number);
        result += characters.charAt(index);
    }
    return result;
}

// 수정 버튼을 눌렀을 때, 기존 작성 내용을 textarea 에 전달합니다.
// 숨길 버튼을 숨기고, 나타낼 버튼을 나타냅니다.
function editPost(id) {
    showEdits(id);
    let contents = $(`#${id}-contents`).text().trim();
    $(`#${id}-textarea`).val(contents);
}

function showEdits(id) {
    $(`#${id}-editarea`).show();
    $(`#${id}-submit`).show();
    $(`#${id}-delete`).show();

    $(`#${id}-contents`).hide();
    $(`#${id}-edit`).hide();
}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// 여기서부터 코드를 작성해주시면 됩니다.

$(document).ready(function () {
    // HTML 문서를 로드할 때마다 실행합니다.
    getMessages();
})

// 메모를 불러와서 보여줍니다.
function getMessages() {
    // 1. 기존 메모 내용을 지웁니다.
    $('#post_box2').empty();
    // 2. 메모 목록을 불러와서 HTML로 붙입니다.
    $.ajax({
        type: 'GET',
        url: '/api/mylogs',
        success: function (response) {
            for (let i = 0; i < response.length; i++) {
                let message = response[i];
                let id = message['id'];
                let username = message['username'];
                let contents = message['contents'];
                let title = message['title']
                let modifiedAt = message['modifiedAt'];
                let createdAt = message['createdAt'];
                addHTML(id, username, contents, title, modifiedAt, createdAt);
            }
        }
    })
}

// 메모 하나를 HTML로 만들어서 body 태그 내 원하는 곳에 붙입니다.
function addHTML(id, username, contents, title, modifiedAt,createdAt) {
    // 1. HTML 태그를 만듭니다.

    let tempHtml = `<div id="post-box" class="container">
        <div class="box">
            <article class="media">
                <div class="media-left">
                </div>
                <div class="media-content">
                    <div class="content">
                        <p>
                            <strong>Title : ${title}</strong> 
                            <br>
                            <br>
                           <small>Date : ${modifiedAt}
                            <br>
                            Username : ${username}
                            </small>
                        </p>
                    </div>
                    <nav class="level is-mobile">
                        <button class="button is-link" id="btn-gathering-detail"
                                onclick='$("#modal-gathering${id}").addClass("is-active")'>자세히 보기
                        </button>
                    </nav>
                </div>
            </article>
        </div>
    </div>
          <div class="modal" id="modal-gathering${id}">
                <div class="modal-background"
                     onclick='$("#modal-gathering${id}").removeClass("is-active")'>
                </div>

                <!--모달 본문-->
                <div class="modal-card">
                    <header class="modal-card-head">
                        <p class="modal-card-title">${title}</p>
                        <button class="delete" aria-label="close"
                                onclick='$("#modal-gathering${id}").removeClass("is-active")'>
                        </button>
                    </header>
                    <section class="modal-card-body">
                        <div class="content">
                            <p>${contents}</p>
                            <br>
                            <br>
                            
                        작성자명 : ${username}
                        <br>
                        작성날짜 : ${modifiedAt}
                        
                        </div>
                    </section>
                    <!--모달 푸터-->
                    <footer class="modal-card-foot">
                        <!--로그인 한 계정과 게시글 생성 계정 일치 여부에 따라 분기-->
                            <button class="button is-success" id="btn-modal-gathering"
                                    onclick='$("#modal-gathering_update${id}").addClass("is-active")'>수정
                            </button>
                            <button class="button is-success" id="btn-modal-gathering"
                                    onclick="deleteOne(${id})">
                                삭제
                            </button>
                        <button class="button" id="btn-cancel"
                                onclick='$("#modal-gathering${id}").removeClass("is-active")'>
                            돌아가기
                        </button>
                    </footer>
                </div>
            </div>
  <div class="modal" id="modal-gathering_update${id}">
            <div class="modal-background"
                 onclick='$("#modal-gathering_update${id}").removeClass("is-active")'></div>
            <div class="modal-card">
                <!--모임카드 수정 상단-->
                <header class="modal-card-head">
                    <p class="modal-card-title" id="title_rev${id}">${title}</p>
                    <button class="delete" aria-label="close"
                            onclick='$("#modal-gathering_update${id}").removeClass("is-active")'></button>
                </header>
                <!--모임카드 수정 입력필드-->
                <section class="modal-card-body">
                    <div class="content" id="modal-gathering_update${id}">
                            <div>제목<input class="input is-small" type="text" id="title_edit" placeholder="${title}"></div>
                            <br>
                            <div>작성자명<input class="input is-small" type="text" id="username_edit" placeholder="${username}"></div>
                            <br>
                            <div>설명 <textarea class="textarea is-small" placeholder="${contents}" rows="10" id="contents_edit"></textarea></div>
                    </div>
                </section>
                <!--모임카드 수정 푸터-->
                <footer class="modal-card-foot">
                        <button class="button is-success" id="btn-modal-gathering"
                            onclick="submitEdit(${id})">저장
                        </button>

                    <button class="button" id="btn-cancel"
                            onclick='$("#modal-gathering_update${id}").removeClass("is-active")'>
                        돌아가기
                    </button>
                </footer>
            </div>
        </div>`;
    // 2. #cards-box 에 HTML을 붙인다.
    $('#post_box2').append(tempHtml);
}

// 메모를 생성합니다.
function writePost() {
    // 1. 작성한 메모를 불러옵니다.
    let title = $('#title').val()
    let agenda = $('#agenda').val()
    let username = $('#username').val()
    // 2. 작성한 메모가 올바른지 isValidContents 함수를 통해 확인합니다.
    if (isValidContents(agenda) == false) {
        return;
    }
    // 3. genRandomName 함수를 통해 익명의 username을 만듭니다.

    // 4. 전달할 data JSON으로 만듭니다.
    let data = {'username': username, 'contents': agenda, 'title': title};

    // 5. POST /api/memos 에 data를 전달합니다.
    $.ajax({
        type: "POST",
        url: "/api/mylogs",
        contentType: "application/json",
        data: JSON.stringify(data),
        success: function (response) {
            alert('게시글이 성공적으로 작성되었습니다.');
            window.location.reload();
        }
    });
}

// 메모를 수정합니다.
function submitEdit(id) {
    // 1. 작성 대상 메모의 username과 contents 를 확인합니다.
    let username = $(`#username_edit`).val();
    let contents = $(`#contents_edit`).val();
    let title = $(`#title_edit`).val();
    // 2. 작성한 메모가 올바른지 isValidContents 함수를 통해 확인합니다.
    if (isValidContents(contents) == false) {
        return;
    }

    // 3. 전달할 data JSON으로 만듭니다.
    let data = {'username': username, 'contents': contents, 'title': title};

    // 4. PUT /api/memos/{id} 에 data를 전달합니다.
    $.ajax({
        type: "PUT",
        url: `/api/mylogs/${id}`,
        contentType: "application/json",
        data: JSON.stringify(data),
        success: function (response) {
            alert('게시글 변경에 성공하였습니다.');
            window.location.reload();
        }
    });
}

// 메모를 삭제합니다.
function deleteOne(id) {
    // 1. DELETE /api/memos/{id} 에 요청해서 메모를 삭제합니다.
    $.ajax({
        type: "DELETE",
        url: `/api/mylogs/${id}`,
        success: function (response) {
            alert('게시글 삭제에 성공하였습니다.');
            window.location.reload();
        }
    })
}