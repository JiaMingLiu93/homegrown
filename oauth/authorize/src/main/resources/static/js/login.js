/*<![CDATA[*/
$(function () {
    console.log("authorize");
    $('.js-login-form').parsley();
    $('.js-login-form').on('submit', function () {
        console.log("submit");
        $.ajax({
            url: basePath+'/authorize',
            type: 'post',
            data: {
                username: $('.js-login-form [name="username"]').val(),
                password: Base64.encode($('.js-login-form [name="password"]').val()),
                client_id: $('.js-login-form [name="client_id"]').val(),
                response_type: $('.js-login-form [name="response_type"]').val(),
                redirect_uri: $('.js-login-form [name="redirect_uri"]').val(),
                state: $('.js-login-form [name="state"]').val(),
                uid: $('.js-login-form [name="uid"]').val()
            },
            success: function(data) {
                if(data.status==1){
                    window.location.href = data.result;
                }else{
                    console.log('error:'+data);
                }
            },
            error:function(jqXHR,data) {
                console.log(jqXHR.responseText);
                switch (jqXHR.status) {
                    case 0:
                        Messenger().post({
                            message: window.i18nFunc('Communication failure'),
                            type: 'error'
                        });
                        break;
                    case 401:
                        window.location.href = '/login';
                        break;
                    case 403:
                        Messenger().post({
                            message: '用户无权限！',
                            type: 'error'
                        });
                        break;
                    case 404:
                        Messenger().post({
                            message: '404!',
                            type: 'error'
                        });
                        break;
                    case 500:
                        var obj = eval('(' + jqXHR.responseText + ')');
                        Messenger().post({
                           message: obj.result,
                           type: 'error'
                        });
                        break;
                    case 503:
                        var _match = jqXHR.responseText.match(/(.+), Service\(/);
                        Messenger().post({
                            message: _match ? _match[1] : window.i18nFunc('Communication failure'),
                            type: 'error'
                        });
                        break;
                    default:
                        break
                }
            }
        });

        return false
    });

});