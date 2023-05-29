import * as vscode from 'vscode';

export function activate(context: vscode.ExtensionContext) {

	const provider = new GPTIntViewProvider(context.extensionUri);

	context.subscriptions.push(
		vscode.window.registerWebviewViewProvider(GPTIntViewProvider.viewType, provider));

    context.subscriptions.push(
      vscode.commands.registerCommand('GPTInt.clearConvaersation', () => {
        provider.clearColors();
      }));

}

class GPTIntViewProvider implements vscode.WebviewViewProvider {

	public static readonly viewType = 'gptInt.conversationView';

	private _view?: vscode.WebviewView;

	constructor(
		private readonly _extensionUri: vscode.Uri,
	) { }

	public resolveWebviewView(
		webviewView: vscode.WebviewView,
		context: vscode.WebviewViewResolveContext,
		_token: vscode.CancellationToken,
	) {
		this._view = webviewView;

		webviewView.webview.options = {
			// Allow scripts in the webview
			enableScripts: true,

			localResourceRoots: [
				this._extensionUri
			]
		};

		webviewView.webview.html = this._getHtmlForWebview(webviewView.webview);

	}

  public clearColors() {
		if (this._view) {
			this._view.webview.postMessage({ type: 'clearColors' });
		}
	}

	private _getHtmlForWebview(webview: vscode.Webview) {
		// Get the local path to main script run in the webview, then convert it to a uri we can use in the webview.
		const scriptUri = webview.asWebviewUri(vscode.Uri.joinPath(this._extensionUri, 'media', 'main.js'));
    const markedUri = webview.asWebviewUri(vscode.Uri.joinPath(this._extensionUri, 'media', 'marked.min.js'));
		// Do the same for the stylesheet.
		const styleVSCodeUri = webview.asWebviewUri(vscode.Uri.joinPath(this._extensionUri, 'media', 'vscode.css'));
		const styleMainUri = webview.asWebviewUri(vscode.Uri.joinPath(this._extensionUri, 'media', 'main.css'));

		// Use a nonce to only allow a specific script to be run.
		const nonce = getNonce();

		return `<!DOCTYPE html>
			<html lang="en">
			<head>
				<meta charset="UTF-8">

				<!--
					Use a content security policy to only allow loading styles from our extension directory,
					and only allow scripts that have a specific nonce.
					(See the 'webview-sample' extension sample for img-src content security policy examples)
				-->
				<!-- <meta http-equiv="Content-Security-Policy" content="default-src 'none'; style-src ${webview.cspSource}; script-src 'nonce-${nonce}';">

				<meta name="viewport" content="width=device-width, initial-scale=1.0">-->

				 <link href="${styleVSCodeUri}" rel="stylesheet">
				 <link href="${styleMainUri}" rel="stylesheet"> 
      
				<title>Cat Colors</title>
			</head>
			<body>
      <script src="../resources/marked.min.js"></script>
      <div class="color-frame">
				<ul class="color-list">
				</ul>
      
			<!--	<button class="add-color-button">Add Color</button> 
        <textarea id="system" style="height: 770px; width: 100%;"></textarea>-->

        <textarea id="user" style="position: fixed; bottom: 0; height: 30px;width: 87%; overflow: auto"></textarea>
        <div>
				<script nonce="${nonce}" src="${scriptUri}"></script>
        <script nonce="${nonce}" src="${markedUri}"></script>
			</body>
			</html>`;
	}
}

function getNonce() {
	let text = '';
	const possible = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';
	for (let i = 0; i < 32; i++) {
		text += possible.charAt(Math.floor(Math.random() * possible.length));
	}
	return text;
}