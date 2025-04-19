import systems.danger.kotlin.*

danger(args) {

    var allChecksPassed = true
    val title = github.pullRequest.title.trim()

    if (!title.contains(Regex("Feature|Bug|Refactor", RegexOption.IGNORE_CASE))) {
        warn("PR title should contain 'Feature', 'Bug', or 'Refactor' (case-insensitive).")
        allChecksPassed = false
    }

    if (allChecksPassed) {
        message("✅ All Danger checks passed!")
    }

}
