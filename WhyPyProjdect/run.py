N = 3  # Number of elements
lists = [5, 7, 7]  # List of elements
cnt = 0

while max(lists) != lists[0]:
    lists[lists.index(max(lists))] -= 1
    lists[0] += 1
    cnt += 1

if lists.count(max(lists)) >= 2:
    print(cnt + 1)
else:
    print(cnt)