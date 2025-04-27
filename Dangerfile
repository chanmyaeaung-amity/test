# Dangerfile

# Check PR title
if !github.pr_title.match?(/feature|bug|refactor/i)
  warn("PR title should include 'Feature', 'Bug', or 'Refactor'")
end

# Check for Jira ticket link in PR body
jira_link_regex = %r{https://ekoapp\.atlassian\.net/browse/[A-Z]+-\d+}
if !github.pr_body.match?(jira_link_regex)
  fail("Please include a Jira ticket link in the PR description.")
end

# Check if there is reviewers
pr_json = github.pr_json

individual_reviewers = pr_json["requested_reviewers"] || []
team_reviewers = pr_json["requested_teams"] || []

if individual_reviewers.empty? && team_reviewers.empty?
 warn("🔍 No reviewers assigned. Please request at least one individual or team reviewer.")
end

# Fail if no labels
if github.pr_labels.empty?
  fail("This PR must have at least one label.")
end

# Fail if no milestone
if github.pr_json[:milestone].nil?
  fail("This PR must be assigned to a milestone.")
end

# ✅ All checks passed
if status_report[:warnings].empty? && status_report[:errors].empty?
  message("✅ All Danger checks passed! Great job!")
end
