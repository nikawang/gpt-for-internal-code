// @ts-check
// import marked from 'marked';
// This script will be run within the webview itself
// It cannot access the main VS Code APIs directly.
(function () {
    const vscode = acquireVsCodeApi();

    const oldState = vscode.getState() || { colors: [] };
    let colors = oldState.colors;

    // const systemTextarea = document.querySelector('#system');
    const userTextarea = document.querySelector('#user');


    window.addEventListener('message', event => {
        const message = event.data; // The json data that the extension sent
        switch (message.type) {
            case 'clearColors':
                {
                    colors = [];
                    updateColorList(colors);
                    break;
                }

        }
    });

    userTextarea.addEventListener('keydown', (event) => {
    if (event.key === 'Enter') {
        event.preventDefault();
        const data = {
            query: event.target.value
          };
          
          fetch('https://chat4.nikawang.com/chat-py/query/code', {
            method: 'POST',
            headers: {
              'Content-Type': 'application/json',
            },
            body: JSON.stringify(data)
          }).then(response => response.text())
            .then(data => {
              // 处理返回的数据 
                //将 data 写入文件 
            console.log("response\t"+data);
            const text = "用户提问:\n<b>" + event.target.value + "</b>" + "\n-----------------------------\n" + "系统回答:\n" + data + "\n-----------------------------\n";
            
            colors.push({ value: text});
            updateColorList(colors);
            // const text = "system:\n<b>" + event.target.value + "</b>\n-----------------------------\n";
            // systemTextarea.value += text + '\n';
            event.target.value = '';
            })
            .catch(error => {
              // 处理错误
              console.error(error);
            });
    }
    });


    function updateColorList(colors) {
        const ul = document.querySelector('.color-list');
        ul.textContent = '';
        for (const color of colors) {
            const li = document.createElement('li');
            li.className = 'color-entry';

            const colorPreview = document.createElement('div');
            colorPreview.className = 'color-preview';
            // colorPreview.style.backgroundColor = `#${color.value}`;
            // colorPreview.addEventListener('click', () => {
            //     onColorClicked(color.value);
            // });
            li.appendChild(colorPreview);

            const input = document.createElement('div');
            input.className = 'color-input';
            // input.type = 'text';
            // marked("a");
            // input.innerHTML = marked.marked(color.value);
            input.innerHTML = color.value;
            
            li.appendChild(input);
            ul.appendChild(li);
        }

        // Update the saved state
        vscode.setState({ colors: colors });
    }

    
}());

