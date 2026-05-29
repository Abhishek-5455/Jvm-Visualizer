import Editor from "@monaco-editor/react";

export default function CodeEditor({
  code,
  setCode
}) {

  return (

    <Editor
      height="750px"
      defaultLanguage="java"
      value={code}
      onChange={(value) =>
        setCode(value || "")
      }
      theme="vs-dark"
      options={{
        minimap: {
          enabled: false
        },
        fontSize: 16
      }}
    />

  );
}