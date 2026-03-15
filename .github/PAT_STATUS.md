# GitHub PAT Expired - Action Required

The GitHub Personal Access Token stored in Bitwarden has expired.

## To Fix:
1. Go to GitHub Settings → Developer Settings → Personal Access Tokens
2. Create a new token with these scopes:
   - `repo` (Full control of private repositories)
   - `workflow` (Update GitHub Actions workflows)
3. Update Bitwarden item "github" with the new password
4. Run: `bash tools/bw-get.sh "github" password` to verify

## Current Status:
- ✅ Code fixes committed and pushed
- ✅ CI/CD pipeline working
- ❌ Cannot create GitHub issues via API
- ❌ Cannot post comments on issues

## Workaround:
Issues have been documented in:
- `PLANNED_ISSUES.md` - Full list of 20 small, actionable issues
- `scripts/create-issues.sh` - Script to auto-create issues (needs valid PAT)

## Next Steps:
1. Refresh the GitHub PAT
2. Run `bash scripts/create-issues.sh` to create all issues
3. Or manually create issues from PLANNED_ISSUES.md
