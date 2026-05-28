import Editor from "@monaco-editor/react";

export default function CodeEditor({
  code,
  setCode
}) {

  return (
    <Editor
      height="650px"
      defaultLanguage="java"
      value={code}
      onChange={(value) => setCode(value)}
      theme="vs-dark"
      options={{
        fontSize: 16,
        minimap: {
          enabled: false,
        },
        smoothScrolling: true,
        padding: {
          top: 20,
        },
        fontLigatures: true,
      }}
    />
  );
}