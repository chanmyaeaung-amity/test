import systems.danger.kotlin.*
import systems.danger.kotlin.github.GitHub

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

    println("Pull Request Object: ${github.pullRequest}")

    val reviewers = github.pullRequest.requestedReviewers
    if (reviewers == null || reviewers.isEmpty()) {
        fail("This PR must have at least one reviewer assigned.")
        allChecksPassed = false
    }

    if (allChecksPassed) {
        message("✅ All Danger checks passed!")
    }

}
