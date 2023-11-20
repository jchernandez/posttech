package com.android.rojox.core.utils

import java.lang.Exception

class CoreException(code: CodeException): Exception(code.message) {
}

enum class CodeException(val message: String) {

}