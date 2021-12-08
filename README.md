# TestLspatch
测试LSPatch

app的module为目标app，正常打开显示+号的图标。xposedmoduel的module为 模块，如果模块的`handleInitPackageResources`回调的话，app的图标将会被修改为-号
