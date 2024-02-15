# .txt 파일 경로 목록
$txtFilePaths = @("1.txt", "2.txt") # 실제 파일 경로로 이 배열을 수정하세요.

# 인덱스 초기화 (1부터 시작)
$index = 1

foreach ($txtFilePath in $txtFilePaths) {
    # .txt 파일 이름에서 확장자 제거
    $baseName = [System.IO.Path]::GetFileNameWithoutExtension($txtFilePath)

    # 각 파일에 대한 URL 처리
    Get-Content $txtFilePath | ForEach-Object {
        $url = $_

        # aria2c를 사용하여 파일 임시 다운로드
        $tempPath = "$baseName/menu/$index"
        if (-not (Test-Path $tempPath)) {
            New-Item -ItemType Directory -Force -Path $tempPath | Out-Null
        }
        $tempFile = Join-Path $tempPath "temp.jpg"
        aria2c --continue=true --dir=$tempPath --out="temp.jpg" $url | Out-Null

        # 다운로드된 이미지의 SHA256 해시 계산
        $sha256 = [System.BitConverter]::ToString([System.Security.Cryptography.SHA256]::Create().ComputeHash([System.IO.File]::ReadAllBytes($tempFile))).Replace("-", "").ToLower()

        # 최종 파일 경로 설정
        $finalPath = "$tempPath/$sha256.jpg"
        Move-Item -Path $tempFile -Destination $finalPath

        # 파일의 상대경로 출력
        Write-Output "$finalPath"

        # 인덱스 증가
        $index++
    }
}