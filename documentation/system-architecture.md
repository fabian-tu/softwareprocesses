---
title: "Project Development Guide"
author: [DevOps Team]
date: "2026-03-11"
subject: "Internal Documentation"
keywords: [Markdown, Pandoc, CI/CD]
subtitle: "Standard Operating Procedures for the Develop Branch"
---

# 1. Introduction
This document serves as a mock documentation file to test the **GitHub Actions** Pandoc integration. Once the pipeline runs, this Markdown file will be converted into a formatted PDF.

## 1.1 Development Workflow
Our current workflow follows a specific branching strategy:
* **Main**: Production-ready code.
* **Staging**: Pre-production testing environment.
* **Develop**: Active feature development.

# 2. Technical Specifications
The pipeline is configured to run on a **self-hosted runner**. This ensures we have the necessary environment for Docker builds and document conversion.

### Pipeline Components
| Step | Tool | Purpose |
| :--- | :--- | :--- |
| Checkout | Git | Pulls latest code changes |
| Install | Pandoc | Converts MD to PDF |
| Build | Docker | Packages the application |

# 3. Deployment Instructions
To trigger a new documentation build, simply push a change to any branch matching `develop/**`.

```bash
git add documentation/system-architecture.md
git commit -m "Update docs"
git push origin develop/feature-x