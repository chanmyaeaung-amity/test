# Dangerfile

# Check PR title
if !github.pr_title.match?(/feature|bug|refactor/i)
  warn("PR title should include 'feature', 'bug', or 'refactor'")
end

# Check for Jira ticket link in PR body
jira_link_regex = %r{https://ekoapp\.atlassian\.net/browse/[A-Z]+-\d+}
if !github.pr_body.match?(jira_link_regex)
  warn("Please include a Jira ticket link in the PR description.")
end

# Check if there is reviewers
pr_json = github.pr_json

individual_reviewers = pr_json["requested_reviewers"] || []
team_reviewers = pr_json["requested_teams"] || []

if individual_reviewers.empty? && team_reviewers.empty?
  warn("🔍 No reviewers assigned. Please request at least one individual or team reviewer.")
end

# ✅ All checks passed
if status_report[:warnings].empty? && status_report[:errors].empty?
  message("✅ All Danger checks passed! Great job!")
end
