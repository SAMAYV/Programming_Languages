#include <bits/stdc++.h>
using namespace std;
#define ld long double
#define ll long long
#define REP(i, a, b) for (ll i = a; i < b; i++)
#define REPI(i, a, b) for (ll i = b - 1; i >= a; i--)
#define i_os ios::sync_with_stdio(0);  cin.tie(0);  cout.tie(0);
#define INF (ll)1e18 + 100
#define endl "\n"

int main()
{
	fstream f1,f2,f3,f4;
	f1.open("compare_account.txt",ios::in);
	f2.open("compare_amount.txt",ios::in);
	f3.open("account.txt",ios::in);
	f4.open("amount.txt",ios::in);
	while(!f1.eof() && !f3.eof()){
		ll x,y;
		f1 >> x;
		f3 >> y;
		if(x != y){
			cout << x << " " << y << "\n";
		}
	}
	while(!f2.eof() && !f4.eof()){
		ll x,y;
		f2 >> x;
		f4 >> y;
		if(x != y){
			cout << x << " " << y << "\n";
		}
	}
	f1.close();
	f2.close();
	f3.close();
	f4.close();
	return 0;
}