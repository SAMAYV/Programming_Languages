#pragma GCC optimize("Ofast")
#pragma GCC optimization("unroll-loops")
#pragma GCC optimize("unroll-loops")
#pragma GCC optimize("fast-math")
#pragma GCC optimize("no-stack-protector")

#include <bits/stdc++.h>
using namespace std;
#include <ext/pb_ds/assoc_container.hpp>
#include <ext/pb_ds/tree_policy.hpp>
using namespace __gnu_pbds;
#define ld long double
#define ll long long
#define REP(i, a, b) for (ll i = a; i < b; i++)
#define REPI(i, a, b) for (ll i = b - 1; i >= a; i--)
#define i_os ios::sync_with_stdio(0);  cin.tie(0);  cout.tie(0);
#define INF (ll)1e18 + 100
#define endl "\n"
#define p0(a) cout << a << " "
#define p1(a) cout << a << endl
#define p2(a, b) cout << a << " " << b << endl
#define p3(a, b, c) cout << a << " " << b << " " << c << endl
#define p4(a, b, c, d) cout << a << " " << b << " " << c << " " << d << endl
// SOME BITMASK KNOWLEDGE
// 1)x & (x - 1):sets the last one bit of x to zero
// power of two exactly when x & (x − 1) = 0.
// 2)x & -x:sets all the one bits to zero, except last one bit
// 3)x | (x - 1):inverts all the bits after the last one bit
#define o_set tree<int, null_type, less<int>, rb_tree_tag, tree_order_statistics_node_update>
#define o_setll tree<ll, null_type, less<ll>, rb_tree_tag, tree_order_statistics_node_update>
typedef tree<pair<ll, ll>,null_type,less<pair<ll, ll>>,rb_tree_tag,tree_order_statistics_node_update> indexed_set;
typedef tree<ll,null_type,less<ll>,rb_tree_tag,tree_order_statistics_node_update> indexed_set1;
typedef tree<string,null_type,less<string>,rb_tree_tag,tree_order_statistics_node_update> indexed_set2;
//1. order_of_key(k) : number of elements strictly lesser than k
//2. find_by_order(k) : k-th element in the set
// freopen("input.txt","r",stdin);
// freopen("output.txt","w",stdout);

ll const maxn = 1e9;

int main()
{
	i_os;
	fstream f1,f2,f3;
	f1.open("final0_account.txt",ios::in);
	f2.open("final0_amount.txt",ios::in);
	f3.open("queries1.txt",ios::out);
	vector<pair<ll,ll>> table[10];
	ll account,amount;
	while(!f1.eof() && !f2.eof()){
		f1 >> account;
		f2 >> amount;
		table[account/maxn].push_back({account,amount});
	}
	vector<ll> size(10,10);
	vector<vector<ll>> output;
	REP(i,0,300){
		ll row = rand() % 10;
		ll col = rand() % size[row];
		ll amount = rand() % 100;
		output.push_back({1, table[row][col].first, amount});
	}
	REP(i,0,300){
		ll row = rand() % 10;
		ll col = rand() % size[row];
		ll amount = rand() % 100;
		output.push_back({2, table[row][col].first, amount});
	}
	REP(i,0,300){
		ll row1 = rand() % 10;
		ll col1 = rand() % size[row1];
		ll row2 = rand() % 10;
		ll col2 = rand() % size[row2];
		ll amount = rand() % 100;
		output.push_back({3, table[row1][col1].first, table[row2][col2].first, amount});
	}
	REP(i,0,3){
		ll acc = rand() % 10000000000;
		output.push_back({4, acc});
		table[acc/maxn].push_back({acc,0});
		size[acc/maxn]++;
	}
	random_shuffle(output.begin(),output.end());
	REP(i,0,3){
		ll row = rand() % 10;
		ll col = rand() % size[row];
		output.push_back({5,table[row][col].first});
		pair<ll,ll> p = table[row][col];
		table[row].erase(table[row].begin() + col);
		size[row]--;
	}
	for(vector<ll> v : output){
		for(ll it : v){
			f3 << it << " ";
		}
		f3 << "\n";
	}
	f1.close();
	f2.close();
	f3.close();
	return 0;
}