#!/bin/bash

# 美化输出颜色配置
BOLD=$(tput bold)
RESET=$(tput sgr0)
GREEN=$(tput setaf 2)
RED=$(tput setaf 1)
YELLOW=$(tput setaf 3)
BLUE=$(tput setaf 4)
CYAN=$(tput setaf 6)

# 调试模式开关（设为 true 启用详细输出）
DEBUG=true

# 默认配置
DEFAULT_LOG_FILE="demo.md"
DEFAULT_START_DATE="2025-01-01"
DEFAULT_PROJECT_DIR="."

log() {
  local level="$1"
  local message="$2"
  case "$level" in
    "success") echo "${BOLD}${GREEN}✅ ${message}${RESET}" ;;
    "error") echo "${BOLD}${RED}❌ ${message}${RESET}" >&2 ;;
    "warning") echo "${BOLD}${YELLOW}⚠️ ${message}${RESET}" ;;
    "info") echo "${BOLD}${BLUE}🔧 ${message}${RESET}" ;;
    "debug") $DEBUG && echo "${BOLD}${CYAN}🐞 ${message}${RESET}" ;;
  esac
}

check_dir() {
  if [ ! -d "$1" ]; then
    log error "目录不存在: $1"
    exit 1
  fi
}

validate_date() {
  local date_str="$1"
  local is_valid=1  # 默认无效

  # 1. 检查日期格式 YYYY-MM-DD（正则校验）
  if [[ ! "$date_str" =~ ^[0-9]{4}-[0-9]{2}-[0-9]{2}$ ]]; then
    log error "日期格式错误: $date_str（请使用 YYYY-MM-DD 格式）"
    return 1  # 用return而非exit，避免终止整个脚本
  fi

  # 2. 跨系统验证日期是否有效（兼容Linux/macOS）
  if [[ "$(uname)" == "Darwin" ]]; then
    # macOS（BSD date）用法：-j解析日期，-f指定格式
    date -j -f "%Y-%m-%d" "$date_str" >/dev/null 2>&1
    is_valid=$?
  else
    # Linux（GNU date）用法：-d解析日期
    date -d "$date_str" >/dev/null 2>&1
    is_valid=$?
  fi

  # 3. 判定结果并返回状态
  if [[ $is_valid -ne 0 ]]; then
    log error "无效的日期: $date_str"
    return 1
  fi

  return 0  # 验证通过
}


gengner_log() {
  local log_file="$1"
  local start_date="$2"
  local project_dir="$3"
  
  log info "开始生成提交日志文件: $log_file"
  log info "起始日期: $start_date"
  log info "项目目录: $project_dir"

  # 切换到项目目录
  log debug "正在切换到目录: $project_dir"
  cd "$project_dir" || {
    log error "无法进入目录: $project_dir"
    exit 1
  }
  log debug "当前工作目录: $(pwd)"

  # 检查是否是 Git 仓库
  log debug "检查是否为 Git 仓库..."
  if ! git rev-parse --git-dir > /dev/null 2>&1; then
    log error "$project_dir 不是一个 Git 仓库"
    exit 1
  fi
  log debug "Git 仓库验证通过"

  # 获取 Git 提交的日期列表
  log debug "正在获取 Git 提交记录（起始日期: $start_date)..."
  local dates=$(git log --no-merges --since="$start_date 00:00:00" --date=short --pretty=format:"%ad" 2>&1)
  local git_status=$?
  
  log debug "Git log 命令返回状态: $git_status"
  
  if [ $git_status -ne 0 ]; then
    log error "执行 git log 命令失败"
    log error "错误信息: $dates"
    exit 1
  fi
  
  # 去重并排序
  dates=$(echo "$dates" | sort -u -r)
  
  log debug "找到的日期列表: $dates"

  if [ -z "$dates" ]; then
    log warning "没有找到符合条件的提交记录（起始日期: $start_date）"
    log info "提示: 请检查起始日期是否正确，以及该日期后是否有提交记录"
    return 1
  fi
  
  local date_count=$(echo "$dates" | wc -l)
  log info "找到 $date_count 个有提交记录的日期"

  # 初始化日志文件（使用绝对路径或相对于原始工作目录）
  local output_file="$log_file"
  if [[ ! "$log_file" =~ ^/ ]]; then
    # 如果是相对路径，相对于脚本执行的原始目录
    output_file="$OLDPWD/$log_file"
  fi

  echo "## 更新日志" > "$output_file" || {
    log error "无法创建 $output_file 文件"
    exit 1
  }
  
  echo -e "\n> 日志生成时间: $(date '+%Y-%m-%d %H:%M:%S')" >> "$output_file"
  echo -e "> 起始日期: $start_date" >> "$output_file"
  echo -e "> 项目目录: $project_dir" >> "$output_file"

  # 遍历日期列表
  dateLogsStr="处理日期: "
  local total_commits=0
  local processed_dates=0
  
  for date in $dates; do
    log debug "正在处理日期: $date"
    local gitlog=$(git log --no-merges --pretty=format:"%s (%an)" --grep="^(\|upd\|feat\|fix\|style\|chore\|docs\|refactor\|revert\|wip)" --since="$date 00:00:00" --until="$date 23:59:59" 2>&1)
    local grep_status=$?
    
    dateLogsStr+="$date | "
    processed_dates=$((processed_dates + 1))
    
    if [ -n "$gitlog" ]; then
      local count=$(echo "$gitlog" | wc -l)
      total_commits=$((total_commits + count))
      log debug "  - 找到 $count 条提交记录"
      {
        echo -e "\n### $date 的更新日志\n"
        while read -r line; do
          echo "- $line"
        done <<< "$gitlog"
      } >> "$output_file"
    else
      log debug "  - 该日期没有符合条件的提交"
    fi
  done
  
  log info "已处理 $processed_dates 个日期"
  
  log debug "$dateLogsStr"
  log success "日志文档 $log_file 生成成功（共 $total_commits 条提交记录）"
  echo "${BOLD}━━━━━━━━━━━━━━━━━━━━━━${RESET}"
}

show_usage() {
  cat << EOF
${BOLD}使用方法:${RESET}
  $0 [选项] [位置参数]

${BOLD}选项参数:${RESET}
  -f, --file <文件名>       指定日志文件名（默认: $DEFAULT_LOG_FILE）
  -d, --date <日期>         指定起始日期 YYYY-MM-DD（默认: $DEFAULT_START_DATE）
  -p, --project <目录>      指定项目目录（默认: $DEFAULT_PROJECT_DIR）
  -h, --help                显示此帮助信息

${BOLD}位置参数（按顺序）:${RESET}
  第1个参数    日志文件名
  第2个参数    起始日期
  第3个参数    项目目录
  
  ${YELLOW}注意: 位置参数可用 "-" 占位以使用默认值${RESET}

${BOLD}示例:${RESET}
  # 使用选项参数
  $0 -f changelog.md                           # 仅指定文件名
  $0 -f changelog.md -d 2024-06-01             # 指定文件名和日期
  $0 -d 2024-01-01 -p /path/to/project         # 指定日期和目录
  $0 --file changelog.md --date 2024-06-01 --project /path/to/project
  
  # 使用位置参数
  $0 changelog.md                              # 指定文件名
  $0 changelog.md 2024-06-01                   # 指定文件名和日期
  $0 - 2024-01-01                              # 占位符：使用默认文件名
  $0 - - /path/to/project                      # 仅指定项目目录
  
  # 混合使用（选项参数优先级更高）
  $0 -f custom.md changelog.md 2024-01-01      # -f 会覆盖位置参数

${BOLD}注意:${RESET}
  - 日期格式必须为 YYYY-MM-DD
  - 选项参数的优先级高于位置参数
  - 项目目录必须是一个有效的 Git 仓库
  - 文件名会自动添加 .md 后缀（如未包含）
EOF
}

parse_arguments() {
  # 初始化变量
  local log_file=""
  local start_date=""
  local project_dir=""
  local positional_args=()
  
  # 解析选项参数
  while [[ $# -gt 0 ]]; do
    case "$1" in
      -h|--help)
        show_usage
        exit 0
        ;;
      -f|--file)
        if [[ -z "${2:-}" ]] || [[ "$2" =~ ^- ]]; then
          log error "选项 $1 需要一个参数"
          exit 1
        fi
        log_file="$2"
        shift 2
        ;;
      -d|--date)
        if [[ -z "${2:-}" ]] || [[ "$2" =~ ^- ]]; then
          log error "选项 $1 需要一个参数"
          exit 1
        fi
        start_date="$2"
        shift 2
        ;;
      -p|--project)
        if [[ -z "${2:-}" ]] || [[ "$2" =~ ^- ]]; then
          log error "选项 $1 需要一个参数"
          exit 1
        fi
        project_dir="$2"
        shift 2
        ;;
      -*)
        log error "未知选项: $1"
        echo "使用 -h 或 --help 查看帮助信息"
        exit 1
        ;;
      *)
        # 收集位置参数
        positional_args+=("$1")
        shift
        ;;
    esac
  done
  
  # 处理位置参数（如果选项参数未设置）
  if [[ -z "$log_file" ]] && [[ ${#positional_args[@]} -ge 1 ]]; then
    log_file="${positional_args[0]}"
  fi
  
  if [[ -z "$start_date" ]] && [[ ${#positional_args[@]} -ge 2 ]]; then
    start_date="${positional_args[1]}"
  fi
  
  if [[ -z "$project_dir" ]] && [[ ${#positional_args[@]} -ge 3 ]]; then
    project_dir="${positional_args[2]}"
  fi
  
  # 应用默认值
  log_file="${log_file:-$DEFAULT_LOG_FILE}"
  start_date="${start_date:-$DEFAULT_START_DATE}"
  project_dir="${project_dir:-$DEFAULT_PROJECT_DIR}"
  
  # 处理占位符
  [[ "$log_file" == "-" ]] && log_file="$DEFAULT_LOG_FILE"
  [[ "$start_date" == "-" ]] && start_date="$DEFAULT_START_DATE"
  [[ "$project_dir" == "-" ]] && project_dir="$DEFAULT_PROJECT_DIR"
  
  # 自动添加 .md 后缀
  if [[ ! "$log_file" =~ \.md$ ]]; then
    log warning "文件名未以 .md 结尾，自动添加后缀"
    log_file="${log_file}.md"
  fi
  
  # 输出解析结果（通过全局变量）
  PARSED_LOG_FILE="$log_file"
  PARSED_START_DATE="$start_date"
  PARSED_PROJECT_DIR="$project_dir"
}

main() {
  set -euo pipefail
  
  # 解析参数
  parse_arguments "$@"
  
  # 使用解析后的参数
  local log_file="$PARSED_LOG_FILE"
  local start_date="$PARSED_START_DATE"
  local project_dir="$PARSED_PROJECT_DIR"
  
  # 验证日期格式
  validate_date "$start_date"
  
  # 验证目录是否存在
  check_dir "$project_dir"
  
  # 保存原始工作目录
  OLDPWD=$(pwd)
  
  log info "配置信息："
  log info "  - 日志文件: $log_file"
  log info "  - 起始日期: $start_date"
  log info "  - 项目目录: $project_dir"
  echo "${BOLD}━━━━━━━━━━━━━━━━━━━━━━${RESET}"
  
  gengner_log "$log_file" "$start_date" "$project_dir"
}

# 执行主函数
main "$@"
