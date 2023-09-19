N = 4  # Number of elements
lists = [10, 10, 10, 10]  # List of elements
cnt = 0

while max(lists) != lists[0]:
    lists[lists.index(max(lists))] -= 1
    lists[0] += 1
    cnt += 1

if lists.count(max(lists)) >= 2:
    print(cnt + 1)
else:
    print(cnt)