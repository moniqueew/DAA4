"""Строит графики из results/results.csv -> results/plots/*.png (python3 scripts/plots.py)."""
import os
import pandas as pd
import matplotlib
matplotlib.use("Agg")
import matplotlib.pyplot as plt

df = pd.read_csv("results/results.csv")
med = df.groupby(["algorithm", "input", "n"], as_index=False).median(numeric_only=True)
os.makedirs("results/plots", exist_ok=True)


def save(fig, ax, name, title, xlabel, ylabel, takeaway):
    ax.set_title(title)
    ax.set_xlabel(xlabel)
    ax.set_ylabel(ylabel)
    ax.grid(True, alpha=0.3)
    ax.legend()
    fig.text(0.5, 0.005, takeaway, ha="center", fontsize=8, wrap=True)
    fig.tight_layout(rect=(0, 0.05, 1, 1))
    fig.savefig(f"results/plots/{name}.png", dpi=130)
    plt.close(fig)


# 1. Рекурсия: вызовы vs n (лог-ось)
fig, ax = plt.subplots(figsize=(6, 4.5))
for inp in ["random", "similar", "disjoint"]:
    d = med[(med.algorithm == "recursive") & (med.input == inp)]
    ax.plot(d.n, d.ops, "o-", label=inp)
ax.set_yscale("log")
save(fig, ax, "1_recursive_calls", "Recursive Edit Distance: number of calls",
     "string length n (characters)", "recursive calls (log scale)",
     "Takeaway: on a log axis the lines are straight, so growth is exponential.")

# 2. Время B и C vs n
fig, ax = plt.subplots(figsize=(6, 4.5))
for alg in ["table", "tworows"]:
    for inp in ["random", "disjoint"]:
        d = med[(med.algorithm == alg) & (med.input == inp)]
        ax.plot(d.n, d.time_ms, "o-", label=f"{alg}, {inp}")
save(fig, ax, "2_dp_time", "Edit Distance: table vs two rows, time",
     "string length n (characters)", "median time (ms)",
     "Takeaway: both grow quadratically; input type does not change the time much.")

# 3. Память B vs C (лог-ось)
fig, ax = plt.subplots(figsize=(6, 4.5))
for alg in ["table", "tworows"]:
    d = med[(med.algorithm == alg) & (med.input == "random")]
    ax.plot(d.n, d.mem_cells, "o-", label=alg)
ax.set_yscale("log")
save(fig, ax, "3_dp_memory", "Edit Distance: allocated memory",
     "string length n (characters)", "allocated int cells (log scale)",
     "Takeaway: table needs ~n*m cells, two rows only ~2*m, the gap grows with n.")

# 4. Поиск: сравнения vs n (лог-ось по y)
fig, ax = plt.subplots(figsize=(6, 4.5))
for alg in ["naive", "kmp"]:
    for inp in ["random", "worst"]:
        d = med[(med.algorithm == alg) & (med.input == inp)]
        ax.plot(d.n, d.ops, "o-", label=f"{alg}, {inp}")
ax.set_yscale("log")
save(fig, ax, "4_search_comparisons", "String search: character comparisons",
     "text length n (characters)", "character comparisons (log scale)",
     "Takeaway: Naive on the worst input is ~m (=50) times above all other lines.")

# 5. Поиск: сравнения на символ текста (ratio)
fig, ax = plt.subplots(figsize=(6, 4.5))
for alg in ["naive", "kmp"]:
    for inp in ["random", "worst"]:
        d = med[(med.algorithm == alg) & (med.input == inp)]
        ax.plot(d.n, d.ops / d.n, "o-", label=f"{alg}, {inp}")
save(fig, ax, "5_search_ratio", "String search: comparisons per text character",
     "text length n (characters)", "comparisons / n (ratio)",
     "Takeaway: KMP stays below 2 per character; Naive worst stays near m = 50.")

# 6. Поиск: время
fig, ax = plt.subplots(figsize=(6, 4.5))
for alg in ["naive", "kmp"]:
    for inp in ["random", "worst"]:
        d = med[(med.algorithm == alg) & (med.input == inp)]
        ax.plot(d.n, d.time_ms, "o-", label=f"{alg}, {inp}")
ax.set_yscale("log")
save(fig, ax, "6_search_time", "String search: time",
     "text length n (characters)", "median time (ms, log scale)",
     "Takeaway: time follows the comparison counts, all lines are linear in n.")
print("plots saved")
