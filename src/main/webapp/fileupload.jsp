<%--
  Created by IntelliJ IDEA.
  User: Shanika Aththatage
  Date: 8/22/2024
  Time: 3:42 PM
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>File Upload</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background-color: #f8f9fa;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }
        .upload-container {
            background-color: white;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        }
        .message {
            margin-top: 20px;
            font-size: 14px;
        }
    </style>
</head>
<body>

<div class="upload-container">
    <h2 class="text-center mb-4">File Upload</h2>
    <form id="uploadForm" enctype="multipart/form-data">
        <div class="mb-3">
            <label for="name" class="form-label">File Name</label>
            <input type="text" class="form-control" id="name" name="name" placeholder="Enter file name" required>
        </div>

        <div class="mb-3">
            <label for="type" class="form-label">File Type</label>
            <input type="text" class="form-control" id="type" name="type" placeholder="Enter file type (e.g., jpg, png)">
        </div>

        <div class="mb-3">
            <label for="file" class="form-label">Choose File</label>
            <input class="form-control" type="file" id="file" name="file" required>
        </div>

        <button type="submit" class="btn btn-primary w-100">Upload</button>
    </form>

    <div class="message text-center text-success" id="message"></div>

    <%-- Display the download button after file upload --%>
    <div id="downloadSection" class="text-center mt-4" style="display: none;">
        <h5>Download your file:</h5>
        <form action="/downloadFile" method="get">
            <input type="hidden" name="fileName" id="downloadFileName">
            <button type="submit" class="btn btn-success">Download File</button>
        </form>
    </div>
</div>




<!-- Bootstrap JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<script>
    document.getElementById('uploadForm').addEventListener('submit', function(event) {
        event.preventDefault();

        const form = document.getElementById('uploadForm');
        const formData = new FormData(form);

        fetch('/multiPartFile', {
            method: 'POST',
            body: formData
        })
            .then(response => response.text())
            .then(data => {
                document.getElementById('message').textContent = data;
                document.getElementById('message').classList.remove('text-danger');
                document.getElementById('message').classList.add('text-success');

                //download
                const fileName = formData.get('name') + (formData.get('type') ? '.' + formData.get('type') : '');

                document.getElementById('downloadFileName').value = fileName;
                document.getElementById('downloadSection').style.display = 'block';
            })
            .catch(error => {
                document.getElementById('message').textContent = 'Error: ' + error;
                document.getElementById('message').classList.remove('text-success');
                document.getElementById('message').classList.add('text-danger');
            });
    });
</script>

</body>
</html>
