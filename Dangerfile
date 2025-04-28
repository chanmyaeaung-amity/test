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

protected_branches = ["main", "production"]

if protected_branches.include?(github.branch_for_base)
  fail("PRs must not target protected branches like #{protected_branches.join(', ')}!")
end


# Fail if no labels
if github.pr_labels.empty?
  fail("This PR must have at least one label.")
end

# Fail if no milestone
if github.pr_json[:milestone].nil?
  fail("This PR must be assigned to a milestone.")
end

warn("Big PR 🚨 (#{git.lines_of_code} lines changed). Consider splitting it into smaller PRs.") if git.lines_of_code > 2


bitmap_files = (git.added_files + git.modified_files).select { |path| path =~ %r{^res/drawable.*/.*\.png$} }

# Check their size (200 KB = 200 * 1024 bytes)
large_bitmaps = bitmap_files.select { |path| File.exist?(path) && File.size(path) > 200 * 1024 }

# Warn if any large bitmap files found
warn("Large bitmap images detected (>200KB): #{large_bitmaps.join(', ')}") if large_bitmaps.any?


# ✅ All checks passed
if status_report[:warnings].empty? && status_report[:errors].empty?
  message("✅ All Danger checks passed! Great job!")
end
