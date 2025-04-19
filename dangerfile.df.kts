import systems.danger.kotlin.*

danger(args) {
    // Warn if no changelog was modified
    warnIfNoChangelog()

    // Fail if PR is too large
    failIfLargePR()

    // Warn if PR title doesn't follow convention
    checkPRTitle()

    // Highlight any .gradle or build config changes
    highlightBuildConfigChanges()

    // Mention author if editing core files
    notifyOnSensitiveFileChanges()
}

// --- Rules ---

fun DangerDSL.warnIfNoChangelog() {
    val hasChangelogUpdate = git.modifiedFiles.any { it.contains("CHANGELOG.md", ignoreCase = true) }
    if (!hasChangelogUpdate) {
        warn("No `CHANGELOG.md` update – please consider adding one if relevant.")
    }
}

fun DangerDSL.failIfLargePR() {
    val totalChanges = git.modifiedFiles.size + git.createdFiles.size + git.deletedFiles.size
    if (totalChanges > 50) {
        fail("This PR is too large ($totalChanges files changed) – consider splitting it up.")
    }
}

fun DangerDSL.checkPRTitle() {
    val title = github.pr.title
    if (!title.matches(Regex("\\[(FEATURE|FIX|DOC|REFACTOR)] .+"))) {
        warn("PR title should start with [FEATURE], [FIX], [DOC], or [REFACTOR] – current: `$title`")
    }
}

fun DangerDSL.highlightBuildConfigChanges() {
    val buildFiles = git.modifiedFiles.filter { it.contains("build.gradle") || it.contains("gradle.properties") }
    if (buildFiles.isNotEmpty()) {
        message(":wrench: Build config changed: ${buildFiles.joinToString(", ")}")
    }
}

fun DangerDSL.notifyOnSensitiveFileChanges() {
    val sensitive = listOf("src/main/AndroidManifest.xml", "proguard-rules.pro")
    val touched = sensitive.filter { file -> git.modifiedFiles.any { it == file } }
    if (touched.isNotEmpty()) {
        warn("You modified sensitive files: ${touched.joinToString(", ")} – double check the changes.")
    }
}
