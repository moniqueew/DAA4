# DAA Assignment 4 — Edit Distance and String Search

GitHub: <ВСТАВЬТЕ_ССЫЛКУ_НА_РЕПОЗИТОРИЙ> (branch `main`, tag `v1.0`)

## Что внутри
- `src/main/java/daa` — Edit Distance (`EditDistanceRecursive`, `EditDistanceTable`, `EditDistanceTwoRows`), поиск (`NaiveSearch`, `KmpSearch`), `Generator` (seed фиксирован), `Experiment`.
- `src/test/java/daa` — JUnit 5 тесты (эталонные значения, пустые строки, кросс-проверки на 100 случайных входах).
- `results/results.csv`, `results/plots/*.png`, `REPORT.md`.

## Сборка и тесты (нужны Java 17+ и Maven)
```
mvn test
```

## Запуск эксперимента и графиков
```
mvn compile exec:java        # пишет results/results.csv (около минуты)
python3 scripts/plots.py     # нужны pandas и matplotlib; пишет results/plots/*.png
```
Одна и та же команда даёт одни и те же счётчики (seed = 42 + n); время, естественно, чуть отличается.

## Использование ИИ
Код, тесты, скрипт графиков и черновик отчёта подготовлены с помощью ИИ-ассистента (Claude). Эксперимент запускался реально, числа в отчёте взяты из `results/results.csv`. (Отредактируйте этот пункт под себя: опишите, что именно вы проверили и поняли сами.)
