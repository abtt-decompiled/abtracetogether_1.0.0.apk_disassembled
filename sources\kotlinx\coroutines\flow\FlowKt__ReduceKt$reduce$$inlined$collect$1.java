package kotlinx.coroutines.flow;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Ref.ObjectRef;
import kotlinx.coroutines.flow.internal.NullSurrogateKt;

@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u001b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\b\u0003\n\u0002\b\u0003*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00028\u00000\u0001J\u0019\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00028\u0000H@ø\u0001\u0000¢\u0006\u0002\u0010\u0005\u0002\u0004\n\u0002\b\u0019¨\u0006\u0006¸\u0006\u0000"}, d2 = {"kotlinx/coroutines/flow/FlowKt__CollectKt$collect$3", "Lkotlinx/coroutines/flow/FlowCollector;", "emit", "", "value", "(Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "kotlinx-coroutines-core"}, k = 1, mv = {1, 1, 15})
/* compiled from: Collect.kt */
public final class FlowKt__ReduceKt$reduce$$inlined$collect$1 implements FlowCollector<T> {
    final /* synthetic */ ObjectRef $accumulator$inlined;
    final /* synthetic */ Function3 $operation$inlined;

    /* JADX WARNING: Removed duplicated region for block: B:15:0x005c  */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x0092 A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0025  */
    public Object emit(Object obj, Continuation continuation) {
        AnonymousClass1 r0;
        int i;
        ObjectRef objectRef;
        ObjectRef objectRef2;
        if (continuation instanceof AnonymousClass1) {
            r0 = (AnonymousClass1) continuation;
            if ((r0.label & Integer.MIN_VALUE) != 0) {
                r0.label -= Integer.MIN_VALUE;
                Object obj2 = r0.result;
                Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                i = r0.label;
                if (i != 0) {
                    ResultKt.throwOnFailure(obj2);
                    Continuation continuation2 = r0;
                    objectRef = this.$accumulator$inlined;
                    if (objectRef.element != NullSurrogateKt.NULL) {
                        Function3 function3 = this.$operation$inlined;
                        T t = this.$accumulator$inlined.element;
                        r0.L$0 = this;
                        r0.L$1 = obj;
                        r0.L$2 = continuation2;
                        r0.L$3 = obj;
                        r0.L$4 = objectRef;
                        r0.label = 2;
                        r0.L$0 = this;
                        r0.L$1 = obj;
                        r0.L$2 = continuation2;
                        r0.L$3 = obj;
                        r0.L$4 = objectRef;
                        r0.label = 1;
                        obj2 = function3.invoke(t, obj, r0);
                        if (obj2 == coroutine_suspended) {
                            return coroutine_suspended;
                        }
                        objectRef2 = objectRef;
                        if (obj2 == coroutine_suspended) {
                        }
                    }
                    objectRef.element = obj;
                    return Unit.INSTANCE;
                } else if (i == 1) {
                    objectRef2 = (ObjectRef) r0.L$4;
                    Object obj3 = r0.L$3;
                    Continuation continuation3 = (Continuation) r0.L$2;
                    Object obj4 = r0.L$1;
                    FlowKt__ReduceKt$reduce$$inlined$collect$1 flowKt__ReduceKt$reduce$$inlined$collect$1 = (FlowKt__ReduceKt$reduce$$inlined$collect$1) r0.L$0;
                    ResultKt.throwOnFailure(obj2);
                    if (obj2 == coroutine_suspended) {
                        return coroutine_suspended;
                    }
                } else if (i == 2) {
                    objectRef2 = (ObjectRef) r0.L$4;
                    Object obj5 = r0.L$3;
                    Continuation continuation4 = (Continuation) r0.L$2;
                    Object obj6 = r0.L$1;
                    FlowKt__ReduceKt$reduce$$inlined$collect$1 flowKt__ReduceKt$reduce$$inlined$collect$12 = (FlowKt__ReduceKt$reduce$$inlined$collect$1) r0.L$0;
                    ResultKt.throwOnFailure(obj2);
                } else {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                Object obj7 = obj2;
                ObjectRef objectRef3 = objectRef2;
                obj = obj7;
                objectRef = objectRef3;
                objectRef.element = obj;
                return Unit.INSTANCE;
            }
        }
        r0 = new ContinuationImpl(this, continuation) {
            Object L$0;
            Object L$1;
            Object L$2;
            Object L$3;
            Object L$4;
            int label;
            /* synthetic */ Object result;
            final /* synthetic */ FlowKt__ReduceKt$reduce$$inlined$collect$1 this$0;

            {
                this.this$0 = r1;
            }

            public final Object invokeSuspend(Object obj) {
                this.result = obj;
                this.label |= Integer.MIN_VALUE;
                return this.this$0.emit(null, this);
            }
        };
        Object obj22 = r0.result;
        Object coroutine_suspended2 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        i = r0.label;
        if (i != 0) {
        }
        Object obj72 = obj22;
        ObjectRef objectRef32 = objectRef2;
        obj = obj72;
        objectRef = objectRef32;
        objectRef.element = obj;
        return Unit.INSTANCE;
    }

    public FlowKt__ReduceKt$reduce$$inlined$collect$1(ObjectRef objectRef, Function3 function3) {
        this.$accumulator$inlined = objectRef;
        this.$operation$inlined = function3;
    }
}
