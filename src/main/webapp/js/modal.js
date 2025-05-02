$(function(){
  // 変数に要素を入れる
  var open = $('.modal-open'),
      close = $('.modal-close'),
      container = $('.modal-container'),
      answerForm = $('#answerForm'),
      answerInput = $('#answerInput'),
      nextButton = $('.extra-close[data-next="true"]');  // 次へボタン

  // モーダルを開くボタンがクリックされたら
  open.on('click', function() { 
    // ユーザーが選んだ選択肢の番号を取得
    var selectedAnswer = $(this).data('answer');
    answerInput.val(selectedAnswer);  // 選択した答えをフォームに設定

    // フォームをPOSTして解説を読み込む
    $.post('question', answerForm.serialize(), function(data) {
      // 解説内容をモーダル内にセット
      $('.modal-content').html(data);
      container.addClass('active');  // モーダルを表示
    });

    return false;  // デフォルトのリンク動作を無効にする
  });

  // モーダルを閉じるボタンがクリックされたら
  close.on('click', function() {  
    container.removeClass('active');  // モーダルを閉じる
  });

  // モーダルの外側をクリックしたらモーダルを閉じる
  $(document).on('click', function(e) {
    if (!$(e.target).closest('.modal-body').length) {
      container.removeClass('active');
    }
  });

  // 次の問題へボタンがクリックされたら
  nextButton.on('click', function() {
    // モーダルを閉じて次の問題に進む
    container.removeClass('active');

    // 次の問題へ進むためのリダイレクト
    location.href = 'question?next=true';  // 次の問題をロードする
  });
});

