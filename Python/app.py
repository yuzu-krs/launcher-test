import os
import shutil

def get_unique_filename(directory, filename):
    base, extension = os.path.splitext(filename)
    counter = 1
    unique_filename = filename
    while os.path.exists(os.path.join(directory, unique_filename)):
        unique_filename = f"{base}_{counter}{extension}"
        counter += 1
    return unique_filename

def collect_jar_files(src_dir, dest_dir):
    # コピー先のディレクトリが存在しない場合は作成
    if not os.path.exists(dest_dir):
        os.makedirs(dest_dir)
    
    # 指定ディレクトリ配下のすべてのファイルを探索
    for root, _, files in os.walk(src_dir):
        for file in files:
            if file.endswith('.dll'):
                # JARファイルをコピー
                src_file_path = os.path.join(root, file)
                unique_filename = get_unique_filename(dest_dir, file)
                dest_file_path = os.path.join(dest_dir, unique_filename)
                shutil.copy(src_file_path, dest_file_path)
                print(f"Copied {src_file_path} to {dest_file_path}")

if __name__ == "__main__":
    # 指定されたディレクトリ
    source_dir = r"C:\Users\yztim\OneDrive\デスクトップ\YuzuClient"
    destination_dir = os.path.join(source_dir, "collectedJars")
    
    collect_jar_files(source_dir, destination_dir)