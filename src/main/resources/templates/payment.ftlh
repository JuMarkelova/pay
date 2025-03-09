<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Депозит</title>
</head>
<body>
<h2>Введите сумму для создания депозита</h2>
<form action="/pay" method="post">
    <input type="number" name="amount" step="0.01" required>
    <button type="submit">Оплатить</button>
</form>
</body>
</html>