jenkins_test

## メモ

### 基本構成

```groovy
pipeline{
	parameters{	// ジョブの実行時に指定するパラメータの定義

	}
	agent{	// 実行するノードの指定など

	}
	stages{	// ジョブの処理内容
		stage("stage name"){	// 手順（この単位でジョブのページに表示される）
			steps{	// この手順として行う処理

			}
			post{	// stepsの処理が終わった時に実行される
				always{	// 成否問わず必ず実行される
					
				}
				success{	// 成功時alwaysの後に実行される
					
				}
				failure{	// 失敗時alwaysの後に実行される
					
				}
			}
		}
	}
	post{	// すべてのstage処理が終わった後に実行される
		always{	// 成否問わず必ず実行される
			
		}
		success{	// 成功時alwaysの後に実行される
			
		}
		failure{	// 失敗時alwaysの後に実行される
			
		}
	}
}
```
- postは使わないなら記入しなくても動く

### パラメータ設定

```groovy
	parameters{
		choice(name: "choiceName", choices: ["choce1", "choce2", "choce3"], description: "選択肢")
		string(name: "stringName", defaultValue: "none", description: "文字列")
		booleanParam(name: "boolName", defaultValue: false, description: "フラグ")
		text(name: 'textName', defaultValue: 'none\nmultiple line', description: '複数行の文字列')
	}
```

### よく使いそうなコマンド

※steps内に記述
- `echo`
	- ログ出力
	- 変数の埋め込みは`${paramName}`
- `sh`
	- シェルスクリプト実行
	- ex : `sh "pwd"`
- `bat`
	- windowsコマンド実行
		- 実行ノードがwindowsならこっち
	- `powershell`コマンドのほうがいい？
- `pwd()`
	- シェルのpwdと同じ
	- 他のコマンドで絶対パスを使うときなどに使う
		- ex : `${pwd()}/example.xxx`
- `git`
	- git clone を行う
	- ex : `git https://github.com/user-name/repositori-name.git`
		- プライベートリポジトリならsshな設定が必要？
- `archiveArtifacts`
	- 成果物を保存する
		- ワークスペースのクリアを行ってもこれで指定したものは残せる
		- 保存した成果物はジョブのページからDLできる
	- ex : `archiveArtifacts result.dat`

※stages外postに記述
- `cleanWs`
	- ワークスペースの削除
	- ex(削除に失敗してもジョブを失敗扱いにしない) : `cleanWs notFailBuild: true`

### 外部ファイルにある関数を呼び出す

1. 外部ファイルを作成する
1. 外部ファイルをロードする
1. 外部ファイル内の関数を実行する

#### 外部ファイル作成

```groovy
// 関数を定義する
def function(arg) {
	// 
}

// 自身を返す
return this
```

#### 外部ファイルのロード&実行

```groovy
script{
	def other = load "otherFile.groovy"
	other.function("test")
}
```

## やりがちなミス

- stepsを書かずに処理を書く
- scriptを書かずにdef等が必要な処理を書く
