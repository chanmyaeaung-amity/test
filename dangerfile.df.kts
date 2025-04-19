import systems.danger.kotlin.*

danger(args) {

    var allChecksPassed = true
    val title = github.pullRequest.title.trim()

    if (!title.contains(Regex("Feature|Bug|Refactor", RegexOption.IGNORE_CASE))) {
        warn("PR title should contain 'Feature', 'Bug', or 'Refactor' (case-insensitive).")
        allChecksPassed = false
    }

    if (!github.pullRequest.body.orEmpty().contains(Regex("https://ekoapp\\.atlassian\\.net/browse/[A-Z]+-\\d+"))) {
        warn("PR description should contain a Jira ticket link")
        allChecksPassed = false
    }

    if (github.requestedReviewers.isNullOrEmpty() && github.requestedTeamReviewers.isNullOrEmpty()) {
        fail("This PR must have at least one reviewer assigned.")
        allChecksPassed = false
    }

    if (allChecksPassed) {
        message("✅ All Danger checks passed!")
    }

}
