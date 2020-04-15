function loadUsers() {
    var request = new XMLHttpRequest();
    request.open('get', '/magenta-smalltalk/api/repos/users-api/search/findUsersByLoginIsLike?template=test', true);
    request.onreadystatechange = function () {
        if (request.readyState === 4) {
            if (request.status === 200) {
                var response = JSON.parse(request.responseText);
                handleLoadUsersResponse(response);
            } else {
                console.log("Failed to load users")
            }
        }
    };
    request.send(null);
}

function handleLoadUsersResponse(response) {
    if (response && response['_embedded'] && response['_embedded']['users-api']) {
        var rootNode = document.getElementById('usersList');
        for (var i = 0; i < response['_embedded']['users-api'].length; ++i) {
            var user = response['_embedded']['users-api'][i];
            var node = document.createElement("P");
            var textnode = document.createTextNode(user.login);
            node.appendChild(textnode);
            rootNode.appendChild(node);
        }
    }
}